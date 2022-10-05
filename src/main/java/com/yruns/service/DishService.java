package com.yruns.service;

import com.yruns.dto.DishDto;
import com.yruns.pojo.Dish;

public interface DishService {

    void saveWithFlavor(DishDto dishDto);
}
