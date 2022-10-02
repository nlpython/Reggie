package com.yruns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yruns.pojo.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * EmployeeMapper
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    @Select("select * from employee where username=#{username} and password=#{password};")
    List<Employee> selectUserByUsernameAndPassword(Employee employee);

//    @Insert("insert into employee(id, username, password, name, phone, sex, id_number, create_time, update_time," +
//            "create_user, update_user) values(null, #{username}, #{password}, #{name}, #{phone}, #{sex}, #{idNumber}," +
//            "#{createTime}, #{updateTime}, #{createUser}, #{updateUser});")
//    int insert(Employee employee);
}
