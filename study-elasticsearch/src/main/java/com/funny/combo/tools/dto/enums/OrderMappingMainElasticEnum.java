package com.funny.combo.tools.dto.enums;


/********
 * 前台查询条件与ES查询key映射
 * 1 精确查询
 * 2 区间查询
 * 3 需要加密查询
 * 4 集合查询
 * 5 中文或英文检索，强制不走分词器，使用keyword查询
 * 7 分词 需要解密查询，特么的，一会加密，一会解密，3是需要加密
 */
public enum OrderMappingMainElasticEnum {

    orderId("orderId", "orderDataEntity.orderId", 1),
    businessId("businessId", "orderDataEntity.businessId", 1),
    holiday("holiday","orderExt.holiday", 1),
    process("process","orderExt.processId", 1),
    restrictType("restrictType","orderExt.restrictType", 1),
    dbSource("dbSource","orderDataEntity.dbSource", 1),


    create_start("create_start", "orderDataEntity.orderCreateTime", 2),
    deal_start("deal_start", "orderCodeInfoEntityList.usedTime", 2),
    payTime_start("payTime_start", "orderDataEntity.paymentConfirmTime", 2),

    customerName("customerName", "orderDataEntity.consNameEncrypt", 3),
    customerPhone("customerPhone", "orderDataEntity.consPhoneEncrypt", 3),
    userName("userName", "orderDataEntity.userName", 7),
    userId("userId", "orderDataEntity.userId", 1),
    orderTypeList("orderTypeList", "orderDataEntity.orderType", 4),
    orderStatus("orderStatus", "orderDataEntity.orderStatusNew", 1),
    orderSourceList("orderSourceList", "orderDataEntity.orderSource", 4),
    dbSourceList("dbSourceList", "orderDataEntity.dbSource", 4),
    businessTypeList("businessTypeList", "orderDataEntity.businessId", 4),

    businessType("businessType", "orderDataEntity.businessId", 1),
    paymentType("paymentType", "orderDataEntity.paymentType", 1),

    orderCategory("orderCategory", "orderDataEntity.orderId", 1),
    sellerIdList("sellerIdList", "orderDataEntity.sellerId", 4),
    sellerId("sellerId","orderDataEntity.sellerId", 1),

    itemId("itemId", "orderItemList.productId", 1),
    brandId("brandId", "orderItemList.brandId", 1),
    seriesId("seriesId", "orderItemList.seriesId", 1),
    specId("specId", "orderItemList.specId", 1),
    itemBatchId("itemBatchId", "orderExt.batchId", 1),
    adChannel("adChannel", "orderExt.adChannel", 1),
    testChannel("testChannel", "orderExt.testChannel", 1),
    pvid("pvid", "orderExt.pvareaId", 1),
    ;


    private String key;
    private String value;
    private int type;

    OrderMappingMainElasticEnum(String key, String value, int type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public static OrderMappingMainElasticEnum getEnum(String key) {
        for (OrderMappingMainElasticEnum ps : OrderMappingMainElasticEnum.values()) {
            if (ps.getKey().equals(key)) {
                return ps;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
