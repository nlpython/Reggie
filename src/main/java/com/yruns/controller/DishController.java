package com.yruns.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public R<Page<DishDto>> selectWithPaging(int page, int pageSize, String dishName) {
        return R.success(dishService.selectWithPaging(page, pageSize, dishName));
    }

    /**
     * 添加菜品
     */
    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("添加菜品成功");
    }

    /**
     * 按id查询
     */
    @GetMapping("/{id}")
    public R<DishDto> selectById(@PathVariable Long id) {
        return R.success(dishService.selectById(id));
    }

    /**
     * 更新菜品
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.update(dishDto);
        return R.success("修改菜品成功");
    }

}
