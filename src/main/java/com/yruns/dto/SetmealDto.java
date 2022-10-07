package com.yruns.dto;

import com.yruns.pojo.Setmeal;
import com.yruns.pojo.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * SetmealDto
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
