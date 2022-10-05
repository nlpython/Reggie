package com.yruns.service.impl;

import com.yruns.dto.DishDto;
import com.yruns.mapper.DishFlavorMapper;
import com.yruns.mapper.DishMapper;
import com.yruns.pojo.Dish;
import com.yruns.pojo.DishFlavor;
import com.yruns.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DishServiceImpl
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

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
}
