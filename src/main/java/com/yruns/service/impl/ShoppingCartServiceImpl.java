package com.yruns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yruns.mapper.ShoppingCartMapper;
import com.yruns.pojo.ShoppingCart;
import com.yruns.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ShoppingCartServiceImpl
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());

        // 查询是否已经存在
        Long dishId = shoppingCart.getDishId();
        if (dishId != null) {
            // 当前接受数据是添加菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart sc = shoppingCartMapper.selectOne(queryWrapper);

        if (sc != null) {
            // 已经存在，number+1
            Integer number = sc.getNumber();
            sc.setNumber(number + 1);
            shoppingCartMapper.updateById(sc);
            shoppingCart = sc;
        } else {
            // 不存在，追加一条
            shoppingCart.setNumber(1);
            shoppingCartMapper.insert(shoppingCart);
        }
        return shoppingCart;
    }

    @Override
    public List<ShoppingCart> selectByUserId(Long currentId) {
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);
        lambdaQueryWrapper.orderByDesc(ShoppingCart::getCreateTime);
        return shoppingCartMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void sub(ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        Long dishId = shoppingCart.getDishId();

        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
            shoppingCart = shoppingCartMapper.selectOne(queryWrapper);
            Integer number = shoppingCart.getNumber();
            shoppingCart.setNumber(number - 1);
            shoppingCartMapper.updateById(shoppingCart);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
            shoppingCart = shoppingCartMapper.selectOne(queryWrapper);
            Integer number = shoppingCart.getNumber();
            shoppingCart.setNumber(number - 1);
            shoppingCartMapper.updateById(shoppingCart);
        }
    }

    @Override
    public void deleteByUserId(Long currentId) {
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);
        shoppingCartMapper.delete(lambdaQueryWrapper);
    }
}
