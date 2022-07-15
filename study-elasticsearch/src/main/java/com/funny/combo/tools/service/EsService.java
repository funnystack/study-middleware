package com.funny.combo.tools.service;


import com.funny.combo.tools.dto.query.EditMainListQuery;
import com.funny.combo.tools.dto.order.EsTurboOrder;

import java.util.List;

public interface EsService {


    boolean importMainData(String orderId, EsTurboOrder esTurboOrder);

    long getMainOrderListCount(EditMainListQuery param);

    List<EsTurboOrder> getMainOrderList(EditMainListQuery param);
}
