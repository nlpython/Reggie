package com.yruns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yruns.pojo.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

    @Select("select * from dish_flavor where dish_id=#{dishId};")
    List<DishFlavor> selectByDishId(Long dishId);
}
