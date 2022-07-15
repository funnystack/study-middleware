package com.funny.combo.tools.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/4.
 */
@Data
public class TurboOrderPriceDetail implements Serializable{

    private static final long serialVersionUID = 6406604122009984396L;
    /**
     * 逻辑主键
     */
    private Long id;
    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 费用名称
     */
    private String priceName;

    /**
     * 费用金额
     */
    private BigDecimal price;
    /**
     * 费用类型 0 不参与尾款计算，1 参与尾款计算
     */
    private Integer priceType;

    private Date createdTime;

    private Date modifiedTime;

    private Long extendId;//'关联order_prices_info.extend_id'

    /**
     * 条目促销id
     */
    private Long articleId;

}
