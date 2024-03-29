package com.funny.study.shardingshpere.controller;

import com.funny.study.shardingshpere.dao.OrderMapper;
import com.funny.study.shardingshpere.entity.OrderEntity;
import com.funny.study.shardingshpere.entity.OrderItemEntity;
import com.funny.study.shardingshpere.service.OrderService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/save")
    @ResponseBody
    public String save() {

        for (int i = 0; i < 100; i++) {
            OrderEntity orderEntity = new OrderEntity();
            Long userId = Long.valueOf((int) (Math.random() * 1000));
            orderEntity.setUserName("test user " + userId);
            orderEntity.setUserId(userId);
            orderEntity.setOrderNo("order Name");
            orderMapper.insert(orderEntity);
        }
        return "success";
    }


    @RequestMapping("/order/item/tx")
    @ResponseBody
    public String orderitem() {
        for (int i = 0; i < 10; i++) {
            OrderEntity orderEntity = new OrderEntity();
            Long userId = Long.valueOf((int) (Math.random() * 1000));
            orderEntity.setUserName("test user " + userId);
            orderEntity.setUserId(userId);
            orderEntity.setOrderNo("order Name");

            List<OrderItemEntity> orderItemEntities = Lists.newArrayList();

            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setUserId(userId);
            orderItemEntity.setItemId(123L);
            orderItemEntity.setItemName("item name");
            orderItemEntity.setOrderId(orderEntity.getOrderId());
            orderItemEntities.add(orderItemEntity);

            orderService.saveOrder(orderEntity, orderItemEntities);
        }
        return "success";
    }


    @RequestMapping("/save")
    @ResponseBody
    public String db() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserName("test user " + 985L);
        orderEntity.setUserId(985L);
        orderEntity.setOrderId(583600660830748672L);
        orderEntity.setOrderNo("order Name");
        orderMapper.insert(orderEntity);


        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setUserName("test user " + 985L);
        orderEntity1.setUserId(985L);
        orderEntity1.setOrderId(583600660830748673L);
        orderEntity1.setOrderNo("order Name ");
        orderMapper.insert(orderEntity1);


        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setUserName("test user " + 986L);
        orderEntity2.setUserId(986L);
        orderEntity2.setOrderId(583600660830748674L);
        orderEntity2.setOrderNo("order Name");
        orderMapper.insert(orderEntity2);


        OrderEntity orderEntity3 = new OrderEntity();
        orderEntity3.setUserName("test user " + 986L);
        orderEntity3.setUserId(986L);
        orderEntity3.setOrderId(583600660830748675L);
        orderEntity3.setOrderNo("order Name");
        orderMapper.insert(orderEntity3);

        return "success";
    }


    @RequestMapping("/save/tx")
    @ResponseBody
    public String saveTx() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserName("test user " + 985L);
        orderEntity.setUserId(985L);
        orderEntity.setOrderId(583600660830748675L);
        orderEntity.setOrderNo("order Name");

        List<OrderItemEntity> orderItemEntities = Lists.newArrayList();

        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setUserId(985L);
        orderItemEntity.setItemId(123L);
        orderItemEntity.setItemName("item name");
        orderItemEntity.setOrderId(583600660830748675L);
        orderItemEntities.add(orderItemEntity);

        orderService.saveOrder(orderEntity, orderItemEntities);

        return "success";
    }


    @GetMapping("/order/{id}")
    public OrderEntity getOrderId(@PathVariable Long id) {
        return orderMapper.findById(id);
    }


    /**
     * long 类型 到前台js 会丢失精度
     *
     * @param userId
     * @return
     */
    @GetMapping("/order/user/{userId}")
    public List<OrderEntity> getUserOrders(@PathVariable Long userId) {
        List<OrderEntity> orderEntityList = orderMapper.findByUserId(userId);
        return orderEntityList;
    }


    @GetMapping("/orders/in")
    public List<OrderEntity> findByOrderIds() {
        return orderMapper.findByOrderIds(Lists.newArrayList(340779241480126464L, 340779267635806209L));
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            System.out.println((int) (Math.random() * 100));
        }
    }
}
