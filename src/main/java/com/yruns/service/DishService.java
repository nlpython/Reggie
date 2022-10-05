package com.yruns.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.dto.DishDto;
import com.yruns.pojo.Dish;

public interface DishService {

    Page<DishDto> selectWithPaging(int page, int pageSize, String name);

    void saveWithFlavor(DishDto dishDto);

    DishDto selectById(Long id);

    void update(DishDto dishDto);
}
