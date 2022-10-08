package com.yruns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yruns.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailsMapper extends BaseMapper<OrderDetail> {

    @Select("select * from order_detail where order_id=#{orderId};")
    List<OrderDetail> selectByOrdersId(Long ordersId);
}
