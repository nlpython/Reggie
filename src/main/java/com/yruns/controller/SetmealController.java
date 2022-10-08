package com.yruns.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.common.R;
import com.yruns.dto.SetmealDto;
import com.yruns.pojo.Employee;
import com.yruns.pojo.Setmeal;
import com.yruns.service.SetmealDishService;
import com.yruns.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * SetmealController
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.save(setmealDto);
        return R.success("添加菜品成功");
    }

    /**
     * 套餐分页查询
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> selectWithPaging(int page, int pageSize, String name) {
        Page<SetmealDto> setmealPage = setmealService.selectWithPaging(page, pageSize, name);
        return R.success(setmealPage);
    }

    /**
     * 删除套餐（批量）
     */
    @DeleteMapping
    public R<String> delete(String ids) {
        String[] id_list = ids.split(",");

        for (String id: id_list) {
            setmealService.delete(Long.parseLong(id));;
        }
        return R.success("删除成功！");
    }

    /**
     * 停售套餐（批量）
     */
    @PostMapping("/status/{code}")
    public R<String> stop(@PathVariable Integer code, String ids) {
        String[] id_list = ids.split(",");

        for (String id: id_list) {
            setmealService.stop(code, Long.parseLong(id));
        }
        return R.success("已停售");
    }


}
