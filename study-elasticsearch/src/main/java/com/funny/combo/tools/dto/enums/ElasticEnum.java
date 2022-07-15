package com.funny.combo.tools.dto.enums;

/**
 * 集群枚举
 * 集群名称: ec-data
 */
public enum ElasticEnum {

    ORDER("common-es", "ec_order", "order"),
    MAINORDER("common-es", "main_order", "order");

    /***
     * 集群
     */
    private String cluster;

    /***
     * 索引
     */
    private String index;

    /***
     * 库
     */
    private String type;

    ElasticEnum(String cluster, String index, String type) {
        this.cluster = cluster;
        this.index = index;
        this.type = type;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
