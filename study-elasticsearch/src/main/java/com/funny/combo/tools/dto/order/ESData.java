package com.funny.combo.tools.dto.order;

/***
 * 数据携带者，保证唯一
 * @author smart
 * @param <T>
 */
public class ESData<T> {

    /**
     * data 唯一值
     */
    public String id;

    /***
     * 数据对象
     */
    public T data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
