package com.yruns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.common.BaseContext;
import com.yruns.common.CustomException;
import com.yruns.dto.OrdersDto;
import com.yruns.mapper.OrderDetailsMapper;
import com.yruns.mapper.OrderMapper;
import com.yruns.mapper.ShoppingCartMapper;
import com.yruns.pojo.*;
import com.yruns.service.AddressService;
import com.yruns.service.OrderService;
import com.yruns.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * OrderServiceImpl
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Override
    @Transactional
    public void submit(Orders orders) {
        // 获取当前用户Id
        Long userId = BaseContext.getCurrentId();
        // 查询当前用户的购物车数据
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByUserId(userId);

        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            throw new CustomException("购物车为空，无法下单");
        }
        // 查询当前用户
        User user = userService.getById(userId);
        // 查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook address = addressService.getById(addressBookId);

        if (address == null) {
            throw new CustomException("地址信息有误，无法下单");
        }

        // 向订单表插入数据
        long orderId = IdWorker.getId();

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setSetmealId(item.getDishId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        orders.setNumber(String.valueOf(orderId));
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setOrderTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(userId);
        orders.setUserName(user.getName());
        orders.setConsignee(address.getConsignee());
        orders.setUserName(user.getName());
        orders.setPhone(user.getPhone());
        orders.setAddress(address.getDetail());
        orderMapper.insert(orders);

        // 向订单明细表插入数据
        for (OrderDetail orderDetail: orderDetails) {
            orderDetailsMapper.insert(orderDetail);
        }

        // 清空购物车数据
        shoppingCartMapper.deleteByUserId(userId);
    }

    @Override
    public Page<OrdersDto> selectWithPaging(int page, int pageSize) {
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>();
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);

        User user = userService.getById(BaseContext.getCurrentId());

        ordersPage = orderMapper.selectPage(ordersPage, queryWrapper);
        BeanUtils.copyProperties(ordersPage, ordersDtoPage, "records");

        List<Orders> ordersList = ordersPage.getRecords();

        List<OrdersDto> ordersDtos = ordersList.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item, ordersDto);

            ordersDto.setUserName(user.getName());
            ordersDto.setPhone(user.getPhone());
            ordersDto.setAddress(item.getAddress());
            ordersDto.setConsignee(item.getConsignee());
            ordersDto.setOrderDetails(orderDetailsMapper.selectByOrdersId(item.getId()));

            return ordersDto;
        }).collect(Collectors.toList());
        ordersDtoPage.setRecords(ordersDtos);

        return ordersDtoPage;
    }
}
