package com.yruns.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yruns.common.BaseContext;
import com.yruns.mapper.AddressMapper;
import com.yruns.pojo.AddressBook;
import com.yruns.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AddressServiceImpl
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public void save(AddressBook addressBook) {
        addressMapper.insert(addressBook);
    }

    @Override
    @Transactional
    public void setDefault(AddressBook addressBook) {
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        updateWrapper.set(AddressBook::getIsDefault, 0);
        // SQL: update address_book set id_default = 0 where user_id = ?;
        addressMapper.update(addressBook, updateWrapper);
        addressBook.setIsDefault(1);
        addressMapper.updateById(addressBook);
    }

    @Override
    public List<AddressBook> selectAllById(Long id) {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null, AddressBook::getUserId, id);
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public AddressBook getDefault(Long userId) {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userId);
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        return addressMapper.selectOne(queryWrapper);
    }

    @Override
    public AddressBook getById(Long id) {
        return addressMapper.selectById(id);
    }
}
