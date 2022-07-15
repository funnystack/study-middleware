package com.funny.combo.tools.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TurboCouponDetail implements Serializable{

	private static final long serialVersionUID = 6729312852414559537L;
	/**
     * 逻辑主键
     */
    private Long id;
	/**
     *订单号
     */
    private Long orderId;

    private String orderIdStr;

    /**
     *优惠类型
     */
    private Integer couponType;
    /**
     * 成本分摊用的，费用谁来承担
     */
    private Integer costType;
    /**
     * 成本价钱
     */
    private BigDecimal costPrice;
    /**
     *优惠金额
     */
    private BigDecimal couponPrice;
    /**
     * 线下优惠金额
     */
    private BigDecimal offlinePrice;

    /**
     * 活动id，批次号等，待确认
     */
    private Integer couponId;
    /**
     * 活动描述
     */
    private String couponDesc;
    /**
     *引用id
     */
    private String refCode;

    private Long itemId;

    private Long skuId;

	private BigDecimal cashBackPrice;//返现

	private BigDecimal interestPrice;//免息红包

	private Integer couponCategory;//红包类型

	private Long extendId;//扩展id

	/**
	 * 购买数量
	 */
	private Integer itemNum;

	/**
	 * 优惠券批次ID
	 */
	private String couponBatchId = "";

	/**
	 * 红包：抵扣车款
	 */
	private BigDecimal deductionPrice;

}
