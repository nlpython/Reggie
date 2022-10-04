package com.yruns.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.common.BaseContext;
import com.yruns.common.R;
import com.yruns.pojo.Employee;
import com.yruns.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * EmployeeController
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @return R<Employee>
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 查询数据库
        Employee emp = employeeService.login(employee);
        if (emp == null) {
            return R.error("用户名不存在或密码错误！");
        }
        // 查看员工状态
        if ("0".equals(emp.getStatus())) {
            return R.error("账户已被禁用");
        }
        request.getSession().setAttribute("userInfo", emp.getId());

        return R.success(emp);
    }

    /**
     * 员工退出
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 清理Session中保存的当前登录员工id
        request.getSession().removeAttribute("userInfo");
        return R.success("退出成功");
    }

    /**
     * 员工添加
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        // 设置初始密码并进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employeeService.addEmployee(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工分页查询
     */
    @GetMapping("/page")
    public R<Page<Employee>> selectWithPaging(int page, int pageSize, String name) {
        Page<Employee> employeePage = employeeService.selectWithPaging(page, pageSize, name);
        return R.success(employeePage);
    }

    /**
     * 启用/禁用操作 添加员工
     */
    @PutMapping
    public R<String> update(HttpServletRequest request ,@RequestBody Employee employee) {

        boolean flag = employeeService.updateById(employee);
        if (flag) {
            return R.success("更新成功");
        } else {
            return R.error("更新失败");
        }
    }

    /**
     * 按id查询
     */
    @GetMapping("/{id}")
    public R<Employee> selectById(@PathVariable Long id) {
        return R.success(employeeService.selectById(id));
    }

}
