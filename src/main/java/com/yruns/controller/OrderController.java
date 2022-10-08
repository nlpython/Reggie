package com.yruns.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.common.R;
import com.yruns.dto.OrdersDto;
import com.yruns.pojo.Orders;
import com.yruns.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderController
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 提交订单
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        orderService.submit(orders);

        return R.success("提交成功");
    }

    /**
     * 查看订单
     */
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> userPage(int page, int pageSize) {
       return R.success(orderService.selectWithPaging(page, pageSize));
    }




}
