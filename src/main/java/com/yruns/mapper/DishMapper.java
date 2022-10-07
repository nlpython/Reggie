package com.yruns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yruns.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    @Select("select count(*) from dish where category_id=#{categoryId};")
    Integer countByCategoryId(Long categoryId);

    @Update("update dish set status=#{code} where id=#{id};")
    void stop(Integer code, Long id);
}
