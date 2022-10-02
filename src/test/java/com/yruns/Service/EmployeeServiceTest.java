package com.yruns.Service;

import com.yruns.pojo.Employee;
import com.yruns.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

/**
 * EmployeeServiceTest
 */
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testLogin() {
        Employee employee = new Employee();
        employee.setUsername("admin");
        employee.setPassword("123456");

        Employee emp = employeeService.login(employee);
        System.out.println(emp);
    }

    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        employee.setUsername("nothing");
        employee.setPhone("185711");
        employee.setName("员工");
        employee.setSex("1");
        employee.setIdNumber("4209212000");
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(1L);
        employee.setUpdateUser(1L);

        boolean b = employeeService.addEmployee(employee);
        System.out.println(b);
    }
}
