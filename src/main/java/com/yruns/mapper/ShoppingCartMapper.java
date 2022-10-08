package com.yruns.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yruns.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    @Select("select * from shopping_cart where user_id=#{userId};")
    List<ShoppingCart> selectByUserId(Long userId);

    @Delete("delete from shopping_cart where user_id=#{userId};")
    void deleteByUserId(Long userId);
}
