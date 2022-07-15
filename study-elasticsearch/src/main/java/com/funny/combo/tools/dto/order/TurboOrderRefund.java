package com.funny.combo.tools.dto.order;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * turbo对外暴露的退款类
 *
 * @author ZhangPeng
 */
@Data
public class TurboOrderRefund implements Serializable {

    private static final long serialVersionUID = 3819226111547106835L;
    /**
     * 退款单号
     */
    private Long refundId;
    /**
     * 订单号
     */
    private Long orderId;
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 商家编号
     */
    private Integer sellerId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 客户姓名
     */
    private String consName;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 车型id
     */
    private Integer specId;

    /**
     * 订单金额
     */
    private BigDecimal orderSum;
    /**
     * 退款金额
     */
    private BigDecimal refundSum;
    /**
     * 退款申请类型，1：用户申请退款，2：超期自动退款
     */
    private Integer refundType;
    /**
     * 第三方支付类型 1：支付宝，2财付通，3连连付
     */
    private Integer payType;
    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款申请状态 1审批中 2成功退款 2 已退款 3退款中 4驳回
     */
    private Integer status;

    /**
     * 第三方的id，根据payType做区分
     */
    private String outPayId;
    /**
     * 实际退款金额
     */
    private BigDecimal refundSumReal;
    /**
     * 申请退款时间
     */
    private Date applyDate;
    /**
     * 实际退款时间
     */
    private Date refundDate;

    /**
     * 退款单创建时间
     */
    private Date createdTime;

    /**
     * 订单创建时间
     */
    private Date orderCreatedTime;

    /**
     * 审核时间
     */
    private Date checkTime ;

    /**
     * 主商品名称
     */
    private String productName;
    /**
     * 退款原因
     */
    private String rejectReason;

    /**
     * 显示同意退款按钮
     */
    private Boolean showAgree;

    /**
     * 显示驳回按钮
     */
    private Boolean showReject;

    /**
     * 显示商家确认收货按钮
     */
    private Boolean showConfirmDelivery;

    /**
     * 订单子类型，主要是子单用，可以为null。
     */
    private Integer subOrderType;

    /**
     * 退款单对应订单的订单状态
     */
    private String adminOrderStatus;

    /**
     * 审批流名称
     */
    private String approveFlowName;

    /**
     * 退货物品数量
     */
    private Integer refundItemNum;

    /**
     * 退货物流公司
     */
    private String logisticsName;

    /**
     * 退货物流单号
     */
    private String logisticsNo;
}
