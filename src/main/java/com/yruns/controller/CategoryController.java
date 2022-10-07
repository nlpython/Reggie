package com.yruns.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.common.R;
import com.yruns.pojo.Category;
import com.yruns.pojo.DishFlavor;
import com.yruns.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * CategoryController: 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品分类
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {

        boolean flag = categoryService.save(category);

        if (flag) {
            return R.success("新增分类成功");
        } else {
            return R.error("新增分类失败");
        }
    }

    /**
     * 菜品分页查询
     */
    @GetMapping("/page")
    public R<Page<Category>> selectWithPaging(int page, int pageSize) {
        Page<Category> CategoryPage = categoryService.selectWithPaging(page, pageSize);
        return R.success(CategoryPage);
    }

    /**
     * 按id查询
     */
    @GetMapping("/{id}")
    public R<Category> selectById(@PathVariable Long id) {
        return R.success(categoryService.selectById(id));
    }

    /**
     * 修改
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.update(category);
        return R.success("更新成功");
    }

    /**
     * 删除
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("id = " + ids);
        boolean delete = categoryService.delete(ids);
        if (delete) {
            return R.success("删除成功");
        } else {
            return R.error("未知原因，删除失败");
        }
    }

    /**
     * 菜品分类
     */
    @GetMapping("/list")
    public R<List<Category>> selectDishFlavor(int type) {
        return R.success(categoryService.selectByType(type));
    }

    /**
     * 套餐分类
     */



}
