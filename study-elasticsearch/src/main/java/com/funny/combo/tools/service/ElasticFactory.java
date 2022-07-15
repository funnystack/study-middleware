package com.funny.combo.tools.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.funny.combo.tools.dto.order.ESData;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/*****
 * @date 2018-01-10
 * @author smart
 * @describe es 实体操作
 * @see
 */
@Component
public class ElasticFactory {

    //es日志操作路径
    public static final Logger logger = LoggerFactory.getLogger("elastic-business");
    /****************************************私有公共区*******************************************/

    /***
     * 返回实体对象
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T response(String text, Class<T> clazz) {
        T response = JSON.parseObject(text,clazz);
        return response;
    }


    /**
     * 范型: 转换结果为集合对象
     * @param text
     * @param clazz
     * @param result
     * @param <T>
     * @return
     */
    private <T> List<T> response(String text, Class<T> clazz,List<T> result) {
        T response = JSON.parseObject(text,clazz);
        if(response != null) {
            if (result == null ) {
                result = new ArrayList<>();
            }
            result.add(response);
        }
        return result;
    }

    /***
     * 验证必填项
     * @param args 可变参数
     * @return
     */
    private boolean validation(String ... args) {
        for (String temp : args) {
            if (StringUtils.isEmpty(temp)) {
                return false;
            }
        }
        return true;
    }

    /*********************公共区***************************************/

    /***
     * 查询：根据文档ID查询数据，注意这里指的_source id
     * @param id
     * @param index
     * @param type
     * @return
     * @throws IOException
     */
    public <T> T queryDocumentById(String index, String type,String id,Class<T> clazz) throws IOException{
        GetRequest getRequest = new GetRequest(index,type,id);
        GetResponse response = client.get(getRequest);
        return this.response(response.getSourceAsString(),clazz);
    }



    /***
     * 浅度分页查询：根据条件查询数据，无数种的查询条件，由调用方决定
     * 最牛逼的支持QueryBuilder，@see QueryBuilder impl
     * @param index
     * @param type
     * @param builder 真正的条件接口，请看实现类数据
     * @return
     * @throws IOException
     */
    public <T> List<T> queryDocumentByBuilder(String index, String type, QueryBuilder builder,Class<T> clazz,int from,int size) throws IOException{
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.query(builder);
        sourceBuilder.sort("orderDataEntity.createTime", SortOrder.DESC);
        sourceBuilder.timeout(new TimeValue(5, TimeUnit.MINUTES));

        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest);
        SearchHits hits = response.getHits();
        List<T> result = new ArrayList<T>();
        for (SearchHit hit:hits) {
            this.response(hit.getSourceAsString(),clazz,result);
        }
        return result;
    }

    /***
     * 浅度分页：大数据量嵌套查询：尽可能的减少new开辟空间
     * 最牛逼的支持QueryBuilder，@see QueryBuilder impl
     * @param
     * @param
     * @param builder 真正的条件接口，请看实现类数据
     * @return
     * @throws IOException
     */
    public <T> List<T> queryDocumentByBuilder(QueryBuilder builder,Class<T> clazz,int from,int size) throws IOException{
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.query(builder);
        sourceBuilder.sort("_id", SortOrder.DESC);
        sourceBuilder.timeout(new TimeValue(5, TimeUnit.MINUTES));

        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest);
        SearchHits hits = response.getHits();
        List<T> result = new ArrayList<T>();
        for (SearchHit hit:hits) {
            this.response(hit.getSourceAsString(),clazz,result);
        }
        return result;
    }


    /****
     * 深度分页，使用游标，动态计算游标基数
     * @param index         索引
     * @param type          类型
     * @param builder       查询条件
     * @param scrollSize    滚动游标
     * @param clazz         实体类
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> List<T> queryDocumentByBuilderScroll(String index,String type, QueryBuilder builder,int scrollSize,Class<T> clazz) throws IOException{
        List<T> result = Lists.newArrayList();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(3L));
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(builder);
        searchSourceBuilder.size(5000);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        if (searchResponse.getHits().totalHits == 0){
            return result;
        } else {
            String scrollId = searchResponse.getScrollId();

            for (SearchHit hit : searchResponse.getHits().getHits()) {
                this.response(hit.getSourceAsString(),clazz,result);
            }

            do {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = client.searchScroll(scrollRequest);

                for (SearchHit hit : searchResponse.getHits().getHits()) {
                    this.response(hit.getSourceAsString(),clazz,result);
                }

            }while (searchResponse.getHits().getHits().length !=0);

            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest);
            boolean succeeded = clearScrollResponse.isSucceeded();
        }

        return result;
    }

    /***
     * 查询：根据条件查询数据，无数种的查询条件，由调用方决定
     * 最牛逼的支持QueryBuilder，@see QueryBuilder impl
     * @param index
     * @param type
     * @param builder 真正的条件接口，请看实现类数据
     * @return
     * @throws IOException
     */
    public long queryDocumentByBuilderCount(String index, String type, QueryBuilder builder) throws IOException{
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(1);
        sourceBuilder.query(builder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest);
        return response.getHits().getTotalHits();
    }


    /***
     * 创建：调用API方式，非REST http
     * @param index
     * @param type
     * @param data
     * @return
     * @throws IOException
     */
    public boolean indexDocument(String index,String type, ESData data) throws IOException{
        String result = indexDocument(index,type,data.getId(), JSON.toJSONString(data.getData()));
        logger.info("----->>>>>ElasticFactory.indexDocument.result {}",result);
        return !StringUtils.isEmpty(result);
    }

    /****
     * 创建：追加索引
     * 向ES发送存储请求，将一个json存储到服务器
     * @param type
     * @param index
     * @param json
     * @return
     */
    public boolean indexDocument(String index,String type, String json) throws IOException{
        String result = indexDocument( index,type,null, json);
        logger.info("----->>>>>ElasticFactory.indexDocument.result {}",result);
        return !StringUtils.isEmpty(result);
    }


    /****
     * 创建：追加索引
     * @param id        唯一标示，可选 1.数据库主键 2. 系统UUID 3.es默认
     * @param type      类型
     * @param index     索引
     * @param json      数据集
     * @return
     */
    private String indexDocument(String index, String type,String id, String json) throws IOException {
        logger.info("----->>>>>ElasticFactory.indexDocument.index {},type {}",index,type);

        if (!validation(index,type) || !isJson(json)) {
            return null;
        }

        /**
         * 若无主键
         * 则默认生成UUID,其实ES本身也支持,es的唯一生成原理是GUID
         */
        logger.info("----->>>>>ElasticFactory.indexDocument.id {},data {}",id,json);
        IndexRequest indexRequest = new IndexRequest(index,type,unique(id))
                .source(json, XContentType.JSON);
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest);
            logger.info("orderId={},第一次写es结果={}",id,JSON.toJSONString(indexResponse));
        } catch (Exception e) {
            try{
                indexResponse = client.index(indexRequest);
                logger.info("orderId={},第二次写es结果={}",id,JSON.toJSONString(indexResponse));
            }catch (Exception e1){
                logger.error("----->>>>>mainOrderIndexDocument id {} ,exception {}",id,e1);
            }
        }
        logger.info("----->>>>>mainOrderIndexDocument id {},status {}",id,(indexResponse==null || indexResponse.status()==null ? "": indexResponse.status().getStatus()));
        return indexResponse==null?"":indexResponse.getId();
    }


    /****
     * 创建：批量执行操作
     * 此中写法仅仅是为了保证某条数据执行失败，不会出现多个相同ID对应相同数据集
     * @param index
     * @param type
     * @param data
     */
    public void indexDocumentBulk(String index,String type,List<ESData> data) throws IOException{

        int count = 1;
        BulkRequest request = null;

        for (ESData temp : data) {

            if (request == null) {
                request = new BulkRequest();
            }

            request.add(new IndexRequest(index, type,temp.getId() ).source(JSON.toJSONString(temp.getData()), XContentType.JSON));

            if (count % 200 == 0) {
                BulkResponse bulkResponse = client.bulk(request);
                request  = new BulkRequest();
            }

            count++;

        }

        BulkResponse bulkResponse = client.bulk(request);

    }


    /***
     * 删除：根据指定ID删除数据
     * @param id
     * @param index
     * @param type
     * @return
     * @throws IOException
     */
    public boolean deleteDocument(String id, String index, String type) throws IOException{
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
        DeleteResponse response = client.delete(deleteRequest);
        return response.status().getStatus() == RestStatus.OK.getStatus();
    }


    /**
     * 更新：根据指定ID，builder更新数据
     * @param id
     * @param index
     * @param type
     * @param builder
     * @return
     * @throws IOException
     */
    public boolean updateDocument(String id, String index, String type,String builder) throws IOException{
        return this.updatePartSourceById(index,type,id,builder);
    }

    /***
     * 更新：局部数据
     * @param index
     * @param type
     * @param id  根据主键更新
     * @param map key:value格式,api 会自动转换map结构为json数据
     */
    public boolean updatePartSourceById(String index,String type,String id,Map<String,String> map) throws IOException {
        return this.updatePartSourceById(index,type,id,map);
    }

    /***
     * 更新：根据主键更新索引
     * @param index
     * @param type
     * @param id
     * @param object
     * @return
     * @throws IOException
     */
    private boolean updatePartSourceById(String index, String type, String id, Object object) throws IOException{
        if (!validation(index,type,id) || object == null) {
            return false;
        }
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc(object);
        return client.update(updateRequest).status().getStatus() == RestStatus.OK.getStatus();
    }


    /***
     * 判断文本内容是否json,es只接收json格式
     * 防止"" 和 {} 的出现
     * @param content
     * @return true:json false:string
     */
    private boolean isJson(String content){
        try {
            return JSONObject.parseObject(content).size() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 生成文档全局唯一ID
     * @param id 入参
     * @return
     */
    private String unique(String id) {
        if (StringUtils.isEmpty(id)) {
            return UUID.randomUUID().toString().replace("-", "");
        }
        return id;
    }

    @Resource
    private RestHighLevelClient client;

}
