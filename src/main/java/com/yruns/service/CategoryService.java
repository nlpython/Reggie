package com.yruns.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yruns.pojo.Category;

import java.util.List;

public interface CategoryService {

    boolean save(Category category);

    Page<Category> selectWithPaging(int page, int pageSize);

    Category selectById(Long id);

    boolean update(Category category);

    boolean delete(Long id);

    List<Category> selectByType(int type);

    String selectNameById(Long id);
}
