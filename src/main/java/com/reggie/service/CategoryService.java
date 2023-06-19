package com.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.common.R;
import com.reggie.entity.Category;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 分类
 */
public interface CategoryService extends IService<Category> {

    //新增分类
    R<String> add(@RequestBody Category category);

    //分类列表查询
    R<Page> pageSelect(int page,int pageSize);

    //根据ID删除分类
    R<String> deleteById( Long id);
}
