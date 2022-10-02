package com.yruns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yruns.mapper.EmployeeMapper;
import com.yruns.pojo.Employee;
import com.yruns.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * EmployeeServiceImpl
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 登录校验密码
     */
    @Override
    public Employee login(Employee employee) {
        // md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        employee.setPassword(password);

        List<Employee> employees = employeeMapper.selectUserByUsernameAndPassword(employee);

        if (employees.size() > 0) {
            return employees.get(0);
        } else {
            return null;
        }
    }

    /**
     * 添加员工
     */
    @Override
    public boolean addEmployee(Employee employee) {
        int insert = employeeMapper.insert(employee);
        return insert > 0;
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Employee> selectWithPaging(int page, int pageSize, String name) {
        // 构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(name != null, Employee::getName, name);
        // 添加排序条件
        queryWrapper.orderByAsc(Employee::getCreateTime);

        // 执行查询
        Page pageResult = employeeMapper.selectPage(pageInfo, queryWrapper);
        return pageResult;
    }

    @Override
    public boolean updateById(Employee employee) {
        return employeeMapper.updateById(employee) > 0;
    }

    @Override
    public Employee selectById(Long id) {
        return employeeMapper.selectById(id);
    }
}
