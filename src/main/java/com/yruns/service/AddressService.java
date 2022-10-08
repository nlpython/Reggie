package com.yruns.service;

import com.yruns.pojo.AddressBook;

import java.util.List;

public interface AddressService {
    void save(AddressBook addressBook);

    void setDefault(AddressBook addressBook);

    List<AddressBook> selectAllById(Long id);

    AddressBook getDefault(Long userId);
}
