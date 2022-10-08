package com.yruns.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.dto.SetmealDto;
import com.yruns.pojo.Setmeal;
import com.yruns.pojo.SetmealDish;

public interface SetmealService {
    Page<SetmealDto> selectWithPaging(int page, int pageSize, String name);

    void save(SetmealDto setmealDto);

    void delete(Long id);

    void stop(Integer code, Long id);
}
