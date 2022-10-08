package com.yruns.service;

import com.yruns.pojo.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart save(ShoppingCart shoppingCart);

    List<ShoppingCart> selectByUserId(Long currentId);

    void sub(ShoppingCart shoppingCart);

    void deleteByUserId(Long currentId);
}
