package com.yruns.controller;

import com.yruns.common.BaseContext;
import com.yruns.common.R;
import com.yruns.pojo.ShoppingCart;
import com.yruns.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ShoppingCartController
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setUserId(BaseContext.getCurrentId());
        ShoppingCart sc = shoppingCartService.save(shoppingCart);
        return R.success(sc);
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCartService.sub(shoppingCart);
        return R.success(shoppingCart);
    }

    /**
     * 展示购物车
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> selectAll() {
        Long currentId = BaseContext.getCurrentId();
        return R.success(shoppingCartService.selectByUserId(currentId));
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    public R<String> delete() {
        Long currentId = BaseContext.getCurrentId();
        shoppingCartService.deleteByUserId(currentId);
        return R.success("清空购物车成功");
    }
}
