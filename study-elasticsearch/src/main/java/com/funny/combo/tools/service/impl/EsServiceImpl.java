package com.funny.combo.tools.service.impl;

import com.alibaba.fastjson.JSON;
import com.funny.combo.tools.dto.order.ESData;
import com.funny.combo.tools.dto.query.EditMainListQuery;
import com.funny.combo.tools.dto.order.EsTurboOrder;
import com.funny.combo.tools.dto.enums.ElasticEnum;
import com.funny.combo.tools.dto.enums.OrderMappingMainElasticEnum;
import com.funny.combo.tools.service.ElasticFactory;
import com.funny.combo.tools.service.EsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service(value = "esService")
public class EsServiceImpl implements EsService {

    @Resource
    protected ElasticFactory elasticFactory;

    private static final Logger logger = LoggerFactory.getLogger(EsServiceImpl.class);

    @Override
    public boolean importMainData(String orderId, EsTurboOrder result) {
        try {
            logger.info("----->>>>>ImportOrderElasticDataHandler.impl.importMainData {} ", orderId);
            if (result != null) {
                ESData data = new ESData();
                data.setId(orderId);
                data.setData(result);
                boolean flag = elasticFactory.indexDocument(ElasticEnum.MAINORDER.getIndex(), ElasticEnum.MAINORDER.getType(), data);
                logger.info("----->>>>>ImportOrderElasticDataHandler.impl.importMainData.flag.id 结果={} ,orderId={} ", flag, orderId);
                return flag;
            }
        } catch (Exception e) {
            logger.error("----->>>>>ImportOrderElasticDataHandler.impl.importMainData.error {} ", e);
        }
        return false;
    }

    @Override
    public long getMainOrderListCount(EditMainListQuery param) {
        QueryBuilder queryBuilder = this.queryMainBuilder(param);
        logger.info("---->>>>>getMainOrderListCount {} , queryBuilder {}", JSON.toJSONString(param), queryBuilder.toString());
        try {
            return elasticFactory.queryDocumentByBuilderCount(ElasticEnum.MAINORDER.getIndex(), ElasticEnum.MAINORDER.getType(), queryBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<EsTurboOrder> getMainOrderList(EditMainListQuery param) {
        QueryBuilder queryBuilder = this.queryMainBuilder(param);
        List<EsTurboOrder> voList = null;
        try {
            int from = (param.getIndex() - 1) * param.getPageSize();
            int size = param.getPageSize();
            voList = elasticFactory.queryDocumentByBuilder(ElasticEnum.MAINORDER.getIndex(), ElasticEnum.MAINORDER.getType(), queryBuilder, EsTurboOrder.class, from, size);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return voList;
    }

    public QueryBuilder queryMainBuilder(EditMainListQuery param) {
        /****
         * 通过fastjson特性，过滤null,减少各种循环映射
         */
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(param), Map.class);
        /**
         * 映射参数查询，避免过多if，冗余代码,以下参数均为动态获取,获取映射关系
         */
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            //过滤前台的特殊select查询,-1代表全部的意思
            if (value == null
                    || StringUtils.isEmpty(value.toString())
                    || "-1".equals(value.toString())
                    || ("0".equals(value.toString()) && !key.equals("restrictType"))
            ) {
                continue;
            }

            OrderMappingMainElasticEnum elastic = OrderMappingMainElasticEnum.getEnum(key);

            if (elastic == null) {
                continue;
            }

            //查询类型
            Integer type = elastic.getType();
            if (type == 1) {
                queryBuilder.must(QueryBuilders.termQuery(elastic.getValue(), value));
            } else if (type == 2) {
                String start = (String) map.get(key);
                String end = (String) map.get(searchKey(key));
                queryBuilder.must(QueryBuilders.rangeQuery(elastic.getValue())
                        .gte(parseDate(start))
                        .lte(parseDate(end)));
            } else if (type == 3) {
                queryBuilder.must(QueryBuilders.matchQuery(elastic.getValue(), value));
            } else if (type == 4) {
                List<Object> list = (List<Object>) value;
                if (CollectionUtils.isEmpty(list)) {
                    continue;
                }
                BoolQueryBuilder builderList = QueryBuilders.boolQuery();
                for (Object temp : list) {
                    builderList.should(QueryBuilders.termQuery(elastic.getValue(), temp));
                }
                queryBuilder.must(builderList);
            } else if (type == 5) {
                queryBuilder.must(QueryBuilders.termQuery(elastic.getValue() + ".keyword", value));
            } else if (type == 7) {
                queryBuilder.must(QueryBuilders.matchQuery(elastic.getValue(), value));
            }
        }

        return queryBuilder;
    }

    /***
     * 订单的查询条件属性不一致，需要转换成指定的规则
     * @param str
     * @return
     */
    private String searchKey(String str) {
        if (str.endsWith("_start")) {
            return str.replace("_start", "_end");
        }
        if (str.endsWith("Start")) {
            return str.replace("Start", "End");
        }
        return str;
    }

    /***
     *
     * @param date
     * @return
     */
    public static long parseDate(String date) {
        if (date == null)
            return 0L;

        try {
            DateFormat parser = new SimpleDateFormat(DEF_DATE_TIME_FORMAT);
            return parser.parse(date).getTime();
        } catch (ParseException ex) {
        }

        return 0L;
    }

    public static final String DEF_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

}
