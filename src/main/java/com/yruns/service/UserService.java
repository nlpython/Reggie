package com.yruns.service;

import com.yruns.pojo.User;

public interface UserService {

    User getOne(String phone);

    void save(User user);
}
