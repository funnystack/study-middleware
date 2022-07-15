package com.funny.combo.tools.dto.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hanshichao
 * @ProjectName order-center
 * @date 2020/9/7 16:46
 */
@Data
public class EditMainListQuery extends PageQuery implements Serializable {

    /**
     * 暂时加上，之后再试能否去掉
     */
    private Integer pageType;

    /**
     * 订单id，对应order_data.order_id
     */
    private String orderId;
    /**
     * 父订单id，对应order_data.origin_order_id
     */
    private String parentOrderId;

    private String create_start;

    /**
     * 下单时间 开始，对应order_data.order_create_time
     */
    private Date orderTimeStart;

    private String create_end;

    /**
     * 下单时间 结束，对应order_data.order_create_time
     */
    private Date orderTimeEnd;

    /**
     * 下单人姓名,对应order_data.cons_name
     */
    private String customerName;
    /**
     * 下单人手机号,对应order_data.cons_phone
     */
    private String customerPhone;

    /**
     * 下单用户id，对应order_data.user_id
     */
    private Long userId;

    /**
     * 用户昵称，对应order_data.user_name
     */
    private String userName;

    /**
     * 多个订单类型,对应order_data.order_type
     */
    private List<Integer> orderTypeList;

    /**
     * 单订单状态
     */
    private Integer orderStatus;

    /**
     * 多个订单状态,对应order_data.order_status
     */
    private List<Integer> orderStatusList;

    /**
     * 订单来源，对应order_data.order_source
     */
    private List<Integer> orderSourceList;

    /**
     * 数据源id，对应order_data.db_source
     */
    private List<Integer> dbSourceList;

    /**
     * 订单业务线，对应order_data.business_id
     */
    private List<Integer> businessTypeList;

    /**
     * 支付类型，对应order_data.payment_type
     */
    private Integer paymentType;

    /**
     * 商品分类，1：车 2：非车
     */
    private Integer orderCategory;

    private Long sellerId;


    /**
     * 商家id，对应order_data.seller_id
     */
    private List<Long> sellerIdList;

    /**
     * 商品id，对应order_item.main_item_id
     */
    private Long itemId;

    /**
     * 品牌id，对应order_item.brand_id
     */
    private Long brandId;

    /**
     * 车系id,order_item.series_id
     */
    private Long seriesId;
    /**
     * 车型id,order_item.spec_id
     */
    private Long specId;

    private Integer holiday;

    private Integer process;

    private String businessType;

    private Integer defaultArgsQuery;

    private String deal_start;
    private String deal_end;
    private String payTime_start;
    private String payTime_end;
    /**
     * 业务退款单号
     */
    private String bizRefundId;
    /**
     * 中台退款单号
     */
    private String mainRefundId;

    /**
     * 是否限制查询
     */
    private Integer restrictType;

    /**
     * 中台退款单状态（order_refund表.refund_status）'退款状态: 0 退款中 1 退款完成
     * @return
     */
    private Integer refundStatus;

    private String adChannel;
    private String itemBatchId;
    private String testChannel;
    private Long pvid;
}
