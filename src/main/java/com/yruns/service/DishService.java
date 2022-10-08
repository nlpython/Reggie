package com.yruns.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.common.R;
import com.yruns.dto.DishDto;
import com.yruns.pojo.Dish;

import java.util.List;

public interface DishService {

    Page<DishDto> selectWithPaging(int page, int pageSize, String name);

    void saveWithFlavor(DishDto dishDto);

    DishDto selectById(Long id);

    void update(DishDto dishDto);

    void delete(Long id);

    void stop(Integer code, Long id);

    List<DishDto> selectByCategoryId(Long categoryId);
}
