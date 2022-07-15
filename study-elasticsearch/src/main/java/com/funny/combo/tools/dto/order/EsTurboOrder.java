package com.funny.combo.tools.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class EsTurboOrder implements Serializable {
    private static final long serialVersionUID = -1678275779165731780L;

    //主数据 接口返回
    private TurboOrderData orderDataEntity = new TurboOrderData();
    //扩展类
    private TurboOrderMark orderExt = new TurboOrderMark();
    private List<TurboOrderItem> orderItemList;
    private List<TurboCouponDetail> couponDetailEntityList;
    private List<TurboOrderPriceDetail> priceDetailEntityList;
    private List<TurboOrderOuterInfo> outerInfoEntityList;


}
