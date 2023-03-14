package com.funny.study.shardingshpere.service;

import com.funny.study.shardingshpere.base.BaseService;
import com.funny.study.shardingshpere.entity.OrderEntity;
import com.funny.study.shardingshpere.entity.OrderItemEntity;

import java.util.List;

public interface OrderService extends BaseService<OrderEntity> {

    int saveOrder(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities);
}
