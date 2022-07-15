package com.funny.combo.tools.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 */
@Data
public class TurboOrderMark implements Serializable {
    private static final long serialVersionUID = -4793773477035042832L;
    /**
     * 是否买断经销
     */
    private Integer isBuyOut;
    /**
     * 定金归属
     */
    private String payTo;
    /**
     * 是否使用车支付(1：是 0：否)
     */
    private String useAutoPay;
    /**
     * 下单顾问
     */
    private Long submitAdviser;
    /**
     * 提车顾问
     */
    private Long consumeAdviser;
    /**
     * 商品上标记的是否需要刷汽车之家pos
     */
    private Integer isTailMoney;
    /**
     * 违约后订金归属 特卖汇和经销商违约后（16,2,4 ）判定  .
     */
    private Long settleTo;
    /**
     * 是否延迟结算，1是,其它都是否
     */
    private Integer settleDelay;
    /**
     * 延迟结算天数
     */
    private Integer settleDelayDayNum;
    /**
     * 是否允许锁定金，AllowLockDepositEnum
     */
    private Integer allowLockDeposit;
    /**
     * 商家上传发票状态
     */
    private Integer sellerInvoiceStatus;
    /**
     * 分期方案类型 InstallmentSchemeTypeEnum
     */
    private Integer installmentSchemeType;
    /**
     * 尾款支付方式
     */
    private Integer restPayMethod;
    /**
     * 活动类型，比如一元半价购，对应枚举marketingTypeEnum
     */
    private Integer marketingType;
    /**
     * 自营金融发货状态 OrderPurchaseStatusEnum
     */
    private Integer purchaseStatus;
    /**
     * 资质审核状态
     */
    private Integer qualificationStatus;
    /**
     * 金融标记，是一期湖南的，还是二期全国的
     * 对应枚举：FinanceTypeEnum
     * @return
     */
    private Integer financeType;
    /**
     * 提车方式
     */
    private Integer deliveryMethod;
    /**
     * 标记金融单是否生成了费用明细(0未生成；1生成)
     */
    private Integer isCreatedFeeDetail;
    /**
     * 认款状态
     */
    private Integer receiveStatus;
    /**
     * 是否需要走交付流程 0-不需要  1-需要
     */
    private Integer deliveryFlow;
    /**
     * 交付物流：0 - 未选择  1-其他物流  2-长久物流
     */
    private Integer deliveryLogistics;
    /**
     * 是否承运：0-未反馈  1-承运  2-不承运
     */
    private Integer isCarriageAccepted;
    /**
     * 尾款支付到哪个账户-1汽车之家,0非汽车之家
     * @return
     */
    private Integer finalPayBy;
    /**
     * 金融审核进度类型
     */
    private Integer financeProcessType;
    /**
     * 商家类型：1-盟主，2-二网，0-其他
     */
    private Integer allianceType;
    /**
     * 金融方案类型
     */
    private Integer financeSchemaType;
    /**
     * 是否线上支付尾款
     */
    private Integer isFinalPayOnline;
    /**
     * 订金是否抵扣车款，0：否，1：是
     */
    private Integer depositType;
    /**
     * 金融结算方式
     */
    private Integer financeChargeType;
    /**
     * 区分消息类型
     */
    private Integer distributeType;
    /**
     * 上传贷款通知状态
     */
    private Integer sellerLoanNoticeStatus;
    /**
     * 促销活动Id
     */
    private Long activityId;
    /**
     * 拼团订单团id
     */
    private Long groupId;
    /**
     * 促销活动类型（1：拼团；2：阶梯购）
     */
    private Integer activityType;
    /**
     * 促销活动状态（1进行中，2成功，3失败，4无效）
     */
    private Integer activityStatus;
    /**
     * 是否自营店铺，商家单独的字段，不同于二网盟主之类
     * @return
     */
    private Boolean selfAuthenticate;
    /**
     * 认款状态变化，true：有变化
     */
    private Boolean returnStatusChanged;
    /**
     * 是否为用户垫付
     */
    private int paymentOnAccount;
    /**
     * 报价单id
     */
    private Long quotationId;
    /**
     * 报价单状态
     */
    private Integer quotationStatus;
    /**
     * 顾问名称
     */
    private String adviserName;
    /**
     * =========================================================
     * 以上是2019-03-18之前的TurboOrder所有字段
     * =========================================================
     */
    private Integer cpsType;

    /**
     * 经销商选择类型 1：可选择 0：不可选
     */
    private Integer dealerSelectType;

    /**
     * 保单初次审核时间
     */
    private Date contractFirstAuditTime;

    /**
     * 是否保价,0 否，1 是
     */
    private Integer isBaoJia;

    /**
     * 是否核销,0 否，1 是
     */
    private Integer checkCodeFlag;
}
