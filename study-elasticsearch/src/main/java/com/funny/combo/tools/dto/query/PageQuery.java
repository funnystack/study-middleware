package com.funny.combo.tools.dto.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页查询封装vo
 * Created by IntelliJ IDEA.
 * User: liyangfan
 * Date: 2012-5-8
 * Time: 15:24:39
 * To change this template use File | Settings | File Templates.
 */
public class PageQuery<T> implements Serializable {
    private static final long serialVersionUID = -2582331870509167197L;
    /**
     * 第几页*
     */
    private Integer index;
    /**
     * 每页大小*
     */
    private Integer pageSize;
    /**
     * 分页开始*
     */
    private Integer start;
    /**
     * 排序字段*
     */
    private String so;
    /**
     * 排序方式*
     */
    private String sp;
    /**
     * 总条数*
     */
    private Integer count;
    /**
     * 当前页结果集*
     */
    private List<T> list;
    /**
     * query参数*
     */
    private Map<String, Object> params;

    public PageQuery() {
        if (this.params == null)
            params = new HashMap<String, Object>();
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
        this.params.put("index", index);
        if (this.index != null && this.pageSize != null)
            createStart();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.params.put("pageSize", pageSize);
        if (this.index != null && this.pageSize != null)
            createStart();
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
        this.params.put("so", so);
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
        this.params.put("sp", sp);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        this.params.put("count", count);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Integer getStart() {
        return start;
    }

    private Integer createStart() {
        this.start = (this.index-1) * this.pageSize;
        this.params.put("start",this.start);
        return this.start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
