package com.yruns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yruns.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    @Select("select count(*) from setmeal where category_id=#{categoryId};")
    Integer countByCategoryId(Long categoryId);
}
