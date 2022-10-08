package com.yruns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.common.R;
import com.yruns.dto.DishDto;
import com.yruns.mapper.DishFlavorMapper;
import com.yruns.mapper.DishMapper;
import com.yruns.pojo.Dish;
import com.yruns.pojo.DishFlavor;
import com.yruns.service.CategoryService;
import com.yruns.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DishServiceImpl
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Page<DishDto> selectWithPaging(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Dish::getName, name);
        lambdaQueryWrapper.orderByAsc(Dish::getSort);

        pageInfo = dishMapper.selectPage(pageInfo, lambdaQueryWrapper);

        // 拷贝
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> dishDtoList = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            String categoryName = categoryService.selectNameById(categoryId);

            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(dishDtoList);

        return dtoPage;
    }

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        // 保存菜品基本信息到dish表
        dishMapper.insert(dishDto);

        // 保存菜品口味数据到dish_flavor
        for (DishFlavor dishFlavor: dishDto.getFlavors()) {
            // 保存dish id
            dishFlavor.setDishId(dishDto.getId());
            dishFlavorMapper.insert(dishFlavor);
        }
    }

    @Override
    @Transactional
    public DishDto selectById(Long id) {
        Dish dish = dishMapper.selectById(id);
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(lambdaQueryWrapper);

        dishDto.setFlavors(dishFlavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void update(DishDto dishDto) {
        // 更新dish表
        dishMapper.updateById(dishDto);

        // 清除原先dish_flavor
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorMapper.delete(lambdaQueryWrapper);

        // 添加新的数据到dish_flavor
        for (DishFlavor flavor: dishDto.getFlavors()) {
            flavor.setDishId(dishDto.getId());
            dishFlavorMapper.insert(flavor);
        }
    }

    @Override
    public void delete(Long id) {
        dishMapper.deleteById(id);
    }

    @Override
    public void stop(Integer code, Long id) {
        dishMapper.stop(code, id);
    }

    @Override
    public List<DishDto> selectByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId, categoryId);
        List<Dish> dishList = dishMapper.selectList(lambdaQueryWrapper);
        List<DishDto> dishDtoList = dishList.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long dishId = item.getId();
            dishDto.setFlavors(dishFlavorMapper.selectByDishId(dishId));
            return dishDto;
        }).collect(Collectors.toList());

        return dishDtoList;
    }
}
