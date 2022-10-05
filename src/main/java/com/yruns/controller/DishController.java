package com.yruns.controller;

import com.yruns.common.R;
import com.yruns.pojo.Dish;
import com.yruns.dto.DishDto;
import com.yruns.service.DishFlavorService;
import com.yruns.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * DishController: 菜品管理
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public R<Dish> selectWithPaging(int page, int pageSize, String dishName) {
        return null;
    }

    /**
     * 添加菜品
     */
    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("添加菜品成功");
    }




}
