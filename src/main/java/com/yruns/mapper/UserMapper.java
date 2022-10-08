package com.yruns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yruns.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where phone=#{phone};")
    User selectOneUserByPhone(String phone);
}
