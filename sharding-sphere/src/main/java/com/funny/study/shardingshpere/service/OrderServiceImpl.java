package com.funny.study.shardingshpere.service;

import com.funny.study.shardingshpere.base.BaseMapper;
import com.funny.study.shardingshpere.base.BaseServiceImpl;
import com.funny.study.shardingshpere.dao.OrderItemMapper;
import com.funny.study.shardingshpere.dao.OrderMapper;
import com.funny.study.shardingshpere.entity.OrderEntity;
import com.funny.study.shardingshpere.entity.OrderItemEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderEntity> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    protected BaseMapper<OrderEntity> baseMapper() {
        return orderMapper;
    }


    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public int saveOrder(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities) {
        int n = 0;
        n += orderMapper.insert(orderEntity);
        if (CollectionUtils.isNotEmpty(orderItemEntities)) {
            for (OrderItemEntity orderItemEntity : orderItemEntities) {
                n += orderItemMapper.insert(orderItemEntity);
            }
        }
        return n;
    }

}
