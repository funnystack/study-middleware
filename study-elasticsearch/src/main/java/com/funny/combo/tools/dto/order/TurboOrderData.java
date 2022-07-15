package com.funny.combo.tools.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 */
@Data
public class TurboOrderData implements Serializable {
    private static final long serialVersionUID = 4386883456143203342L;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 业务线id
     */
    private Integer businessId;
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 子单类型
     */
    private Integer subOrderType;
    /**
     * 支付类型
     */
    private Integer paymentType;
    /**
     * 商品总金额（原始价）
     */
    private BigDecimal totalSum;
    /**
     * 优惠之后的商品总金额（原始价）=totalSum-线上优惠券-线下优惠
     */
    private BigDecimal realTotalSum;
    /**
     * 订单支付的原始金额（使用优惠券之前的）
     */
    private BigDecimal orderSum;
    /**
     * 线上优惠的总金额（不包含线下优惠）
     */
    private BigDecimal couponSum;
    /**
     * 运费
     */
    private BigDecimal freightSum;
    /**
     * 应付金额
     */
    private BigDecimal shouldPay;
    /**
     * 尾款
     */
    private BigDecimal finalPay;
    /**
     * 贷款总额，老的贷款表OrderFinInfo的一个字段
     */
    @Deprecated
    private BigDecimal loanAmount;
    /**
     * 下单时间
     */
    private Date orderCreateTime;
    /**
     * 付款确认时间
     */
    private Date payConfirmTime;
    /**
     * 订单过期时间
     */
    private Date orderExpirationTime;
    /**
     * 订单完成时间
     */
    private Date orderCompleteTime;
    /**
     * 如果还未付款，系统自动取消订单时间
     */
    private Date orderClosedTime;
    /**
     * 订单状态，参见OrderStatusEnum
     */
    private Integer orderStatus;
    /**
     * 订单有效标识（1有效，2无效，3退款锁定），原来的isDelete，参见OrderStatusAllEnum
     */
    private Integer validStatus;
    /**
     * 退款状态 0 未退款 1退款中 2成功退款 3 驳回 4 终止，参见OrderStatusAllEnum
     */
    private Integer refundStatus;
    /**
     * 评论状态:0-未评论  1-已评
     */
    private Integer commentStatus;
    /**
     * 发票上传状态 0：未上传 1：已上传，2 通过 3未通过
     */
    private Integer invoiceStatus;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户登录名
     */
    private String userName;
    /**
     * 送货方式  1：到店提车 2：送货上门
     */
    private Integer deliveryType;
    /**
     * 提车人姓名
     */
    private String customerName;
    /**
     * 提车人电话
     */
    private String customerPhone;
    /**
     * 收货地址
     */
    private String customerAddress;
    /**
     * 父单id
     */
    private Long parentOrderId;
    /**
     * 原始单号(提交订单时的单号)
     */
    private Long originOrderId;
    /**
     * 有效支付时间
     */
    private Date confiredTime ;
    /**
     * 推荐码
     */
    private String invitedCode ;
    /**
     * 平台码
     */
    private String platformCode ;
    /**
     * 平台类型
     */
    private Integer inviteType ;
    /**
     * pvid
     */
    private Long pvid;
    /**
     * 外部来源订单号
     */
    private String externalOrderId;
    /**
     * 锁定金状态（0:商家未锁 or 用户72小时未确认, 1:商家发起锁定订金, 2:用户同意锁定订金）
     */
    private Integer depositLockStatus;
    /**
     * 商家发起锁定订金时间
     */
    private Date depositLockTime;
    /**
     * 商家发起预终止订单时间
     */
    private Date preTerminateTime;
    /**
     * 是否预售标记
     */
    private Integer isPreSell;
    /**
     * 提车开始时间
     */
    private Date pickCarStartTime;
    /**
     * 尾款回调状态 枚举请见NotifyStatusEnum
     */
    private Integer finalpayNotifyStatus;
    /**
     * 促销id
     */
    private Long promotionId;
    /**
     * 阶梯购状态，null 或 0 非阶梯购，1 处理中，2 活动结束;
     */
    private Integer ladderStatus;
    /**
     * 领克下单使用的身份证号
     */
    private String idCard;
    /**
     * 换单前的orderId,即最初单号
     */
    private Long sourceOrderId;
    /**
     * 换过的单号
     */
    private List<Long> changeOrderIdList;
    /**
     * 线下优惠的总金额
     */
    private BigDecimal offCouponSum;
    /**
     * 平安pos直减金额 = 抵扣金额 + 尾款优惠累计
     */
    private BigDecimal posDeduction;
    /**
     * 对外输出的唯一订单状态值，废弃orderStatus，validStatus，refundStatus
     */
    private Integer outputStatus;
    /**
     * 线下翻单 交付店商家id
     */
    private Integer agentSellerId;
    /**
     * 定金付款确认时间
     */
    private Date paymentEarnestTime;
}
