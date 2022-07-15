package com.funny.combo.tools.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单的外部关联信息，比如政策，活动，金融，置换等
 * @author lichao0413
 *
 */
@Data
public class TurboOrderOuterInfo implements Serializable{
	private static final long serialVersionUID = -1024936285590970058L;
	private int type;//类型
	private Long outerId;//外部Id
	private String name;//名字
	private Long orderId;//订单id
	private String orderIdStr;
	private Long itemId;
	private String desc;//序列化的商品权益对象giftrights
	private BigDecimal taxdeduct;//抵扣金额
	private Date createdTime;
}
