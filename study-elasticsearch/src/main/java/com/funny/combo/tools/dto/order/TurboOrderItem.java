package com.funny.combo.tools.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TurboOrderItem implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -3723986900270903084L;
	/**
	 * 明细id，原来的订单itemId
	 */
	private Long detailId;
	/**
	 * 订单号
	 */
	private String orderId;

	/**
	 * sku编号
	 */
	private Long skuId;
	/**
	 * sku名称
	 */
	private String skuName;
	/**
	 * sku类型，对应SkuTypeEnum里的值
	 */
	private Integer skuType;
	/**
	 * 销售属性，老的外观内饰的颜色数据自己转到这个字段里。
	 */
	private String saleProp;
	/**
	 * 图片url
	 */
	private String imageUrl;
	/**
	 * 购买数量
	 */
	private Integer num;
	/**
	 * 原始售价，原来的ecprice
	 */
	private BigDecimal originalPrice;
	/**
	 * 兼容接收我们mq的下游team，这个字段是老的price，他们只用到车，所以这个字段指代线上价格
	 */
	@Deprecated
	private BigDecimal price;
	/**
	 * 折后价格
	 */
	private BigDecimal promotionPrice;
	/**
	 * 线上交易的价格，比如定金
	 */
	private BigDecimal onlinePrice;
	/**
	 * 商品id
	 */
	private Long itemId;
	/**
	 * 是否赠品， 0：非赠品，1：赠品 2套装 3 质保
	 */
	private Integer detailType;
	/**
	 * 外部id
	 */
	private String outerId;
	/**
	 * 车系id
	 */
	private Integer seriesId;
	/**
	 * 车系名称
	 */
	private String seriesName;
	/**
	 * 车型id
	 */
	private Integer specId;
	/**
	 * 车型名称
	 */
	private String  specName;
	/**
	 * 品牌id
	 */
	private Integer brandId;
	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 厂商id
	 */
	private Long factoryId;
	/**
	 * 厂商名字
	 */
	private String factoryName;

	//大区id，名字暂时不需要
	private Long regionalId;

	/**
	 * 提车城市id
	 */
	private Long pickCityId;
	/**
	 * 提车城市名称
	 */
	private String pickCityName;

	/**
	 * 提车地区id
	 */
	private Long pickCountyId;
	/**
	 * 提车地区名称
	 */
	private String pickCountyName;

	private String productName ;


	//以下三个为历史遗留字段，暂时保留
	//金融方案id
	private Integer finPlanId;
	//金融方案描述
	private String finPlanDesc;
	//我的订单--展示字段
	private String finPlanShow;

	/**
	 * 赠品关联的主skuid
	 */
	private Integer parentSkuId;

	/**
	 * 多品类后每个商品的差异化信息(TurboOrderDetailExtend的json串)
	 */
	private String detailExtend;

	/**
	 * 关联order_item_ext.extend_id
	 */
	private Long extendId;

	/**
	 * 关联外order_item.extend_id
	 */
	private Long parentExtendId;

	/**
	 * 结算价
	 */
	private BigDecimal settlementPrice;

	/**
     * 车源id
     * 20180827新模式cps新增
     */
    private Integer carSourceId;

	/**
	 * 结算价
	 */
	private BigDecimal settleSpecialPrice;
	/**
     * 采购价
     */
    private BigDecimal purchasePrice;

	/**
	 * 抵扣券适用车型
	 */
	private String specIds;
}
