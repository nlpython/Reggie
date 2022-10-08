package com.yruns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.dto.SetmealDto;
import com.yruns.mapper.SetmealDishMapper;
import com.yruns.mapper.SetmealMapper;
import com.yruns.pojo.Setmeal;
import com.yruns.pojo.SetmealDish;
import com.yruns.service.CategoryService;
import com.yruns.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SetmealServiceImpl
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private CategoryService categoryService;


    @Override
    public Page<SetmealDto> selectWithPaging(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Setmeal::getName, name);
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        Page<Setmeal> setmealPage = setmealMapper.selectPage(pageInfo, lambdaQueryWrapper);

        // 拷贝
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> dishDtoList = records.stream().map((item) -> {
            SetmealDto dishDto = new SetmealDto();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            String categoryName = categoryService.selectNameById(categoryId);

            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(dishDtoList);

        return setmealDtoPage;
    }

    @Override
    @Transactional
    public void save(SetmealDto setmealDto) {
        setmealMapper.insert(setmealDto);

        Long setmealId = setmealDto.getId();

        for (SetmealDish setmealSetmeal: setmealDto.getSetmealDishes()) {
            setmealSetmeal.setSetmealId(setmealId);
            setmealDishMapper.insert(setmealSetmeal);
        }
    }

    @Override
    @Transactional
    public void delete(Long parseLong) {
        setmealMapper.deleteById(parseLong);
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, parseLong);
        setmealDishMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void stop(Integer code, Long id) {
        setmealMapper.stop(code, id);
    }
}
