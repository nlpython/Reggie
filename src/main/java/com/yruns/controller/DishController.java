package com.yruns.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.common.R;
import com.yruns.pojo.Dish;
import com.yruns.dto.DishDto;
import com.yruns.service.DishFlavorService;
import com.yruns.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DishController: 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
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

    /**
     * 删除菜品（批量）
     */
    @DeleteMapping
    public R<String> delete(String ids) {
        String[] id_list = ids.split(",");

        for (String id: id_list) {
            dishService.delete(Long.parseLong(id));;
        }
        return R.success("删除成功！");
    }

    /**
     * 停售菜品（批量）
     */
    @PostMapping("/status/{code}")
    public R<String> stop(@PathVariable Integer code, String ids) {
        String[] id_list = ids.split(",");

        for (String id: id_list) {
            dishService.stop(code, Long.parseLong(id));
        }
        return R.success("已停售");
    }

    /**
     * 在套餐管理中展示菜品
     */
    @GetMapping("/list")
    public R<List<Dish>> selectByCategoryId(Long categoryId) {
        return R.success(dishService.selectByCategoryId(categoryId));
    }


}
