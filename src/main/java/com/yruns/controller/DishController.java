package com.yruns.controller;

import com.yruns.common.R;
import com.yruns.pojo.Dish;
import com.yruns.pojo.DishFlavor;
import com.yruns.service.DishFlavorService;
import com.yruns.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DishController: 菜品管理
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishFlavorService dishFlavorSerivce;

    @Autowired
    private DishService dishService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public R<Dish> selectWithPaging(int page, int pageSize, String dishName) {
        return null;
    }






}
