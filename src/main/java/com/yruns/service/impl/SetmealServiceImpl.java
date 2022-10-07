package com.yruns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.mapper.SetmealDishMapper;
import com.yruns.mapper.SetmealMapper;
import com.yruns.pojo.Setmeal;
import com.yruns.pojo.SetmealDish;
import com.yruns.dto.SetmealDto;
import com.yruns.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.LambdaMetafactory;
import java.util.Set;

/**
 * SetmealServiceImpl
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public Page<Setmeal> selectWithPaging(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Setmeal::getName, name);
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        Page<Setmeal> setmealPage = setmealMapper.selectPage(pageInfo, lambdaQueryWrapper);
        return setmealPage;
    }

    @Override
    @Transactional
    public void save(SetmealDto setmealDto) {
        setmealMapper.insert(setmealDto);

        Long setmealId = setmealDto.getId();

        for (SetmealDish setmealDish: setmealDto.getSetmealDishes()) {
            setmealDish.setSetmealId(setmealId);
            setmealDishMapper.insert(setmealDish);
        }
    }

    @Override
    public void delete(Long parseLong) {
        setmealMapper.deleteById(parseLong);
    }

    @Override
    public void stop(Integer code, Long id) {
        setmealMapper.stop(code, id);
    }
}
