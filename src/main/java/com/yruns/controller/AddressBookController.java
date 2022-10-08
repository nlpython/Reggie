package com.yruns.controller;

import com.yruns.common.BaseContext;
import com.yruns.common.R;
import com.yruns.pojo.AddressBook;
import com.yruns.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AddressBookController
 */
@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressService addressService;

    /**
     * 新增
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressService.save(addressBook);
        return R.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        log.info(addressBook.toString());
        addressService.setDefault(addressBook);
        log.info(addressBook.toString());
        return R.success(addressBook);
    }

    /**
     * 获取默认地址
     */
    @GetMapping("/default")
    public R<AddressBook> setDefault() {
        return R.success(addressService.getDefault(BaseContext.getCurrentId()));
    }

    /**
     * 地址查询
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        Long id = BaseContext.getCurrentId();
        List<AddressBook> addressBooks = addressService.selectAllById(id);
        return R.success(addressBooks);
    }


}
