package com.yruns.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.pojo.Employee;

/**
 * EmployeeService
 */
public interface EmployeeService {

    Employee login(Employee employee);

    boolean addEmployee(Employee employee);

    Page<Employee> selectWithPaging(int page, int pageSize, String name);

    boolean updateById(Employee employee);

    Employee selectById(Long id);
}
