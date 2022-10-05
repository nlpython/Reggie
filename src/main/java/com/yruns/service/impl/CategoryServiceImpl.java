package com.yruns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.common.CustomException;
import com.yruns.mapper.CategoryMapper;
import com.yruns.mapper.DishMapper;
import com.yruns.mapper.SetmealMapper;
import com.yruns.pojo.Category;
import com.yruns.pojo.Dish;
import com.yruns.service.CategoryService;
import com.yruns.service.DishService;
import com.yruns.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CategoryServiceImpl
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public boolean save(Category category) {
        return categoryMapper.insert(category) > 0;
    }

    @Override
    public Page<Category> selectWithPaging(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getCreateTime);

        return categoryMapper.selectPage(pageInfo, lqw);
    }

    @Override
    public Category selectById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public boolean update(Category category) {
        return categoryMapper.updateById(category) > 0;
    }

    @Override
    public boolean delete(Long id) {
        // 判断当前分类是否关联相关菜品或套餐
        Integer dishNum = dishMapper.countByCategoryId(id);
        Integer setmealNum = setmealMapper.countByCategoryId(id);

        if (dishNum > 0 || setmealNum > 0) {
            // 已经关联，抛出异常
            throw new CustomException("当前分类已经关联相关菜品或套餐, 无法删除");
        } else {
            // 正常删除
            categoryMapper.deleteById(id);
            return true;
        }
//        return categoryMapper.deleteById(id) > 0;
    }

    @Override
    public List<Category> selectByType(int type) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getType, type);

        return categoryMapper.selectList(lambdaQueryWrapper);
    }


}
