package com.funny.study.shardingshpere.dao;

import com.funny.study.shardingshpere.base.BaseMapper;
import com.funny.study.shardingshpere.entity.OrderEntity;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderEntity> {

    List<OrderEntity> findByUserId(Long userId);


    List<OrderEntity> findByOrderIds(List<Long> orderIds);


}