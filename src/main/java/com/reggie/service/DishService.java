package com.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;

/**
 * 菜品管理
 */
public interface DishService extends IService<Dish> {

    //新增菜品信息
    R<String> addDish(DishDto dishDto);

    //菜品信息分页查询
    R<Page> page(int page, int pageSize, String name);
}