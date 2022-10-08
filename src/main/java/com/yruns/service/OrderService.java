package com.yruns.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.dto.OrdersDto;
import com.yruns.pojo.Orders;

public interface OrderService {
    void submit(Orders orders);

    Page<OrdersDto> selectWithPaging(int page, int pageSize);
}
