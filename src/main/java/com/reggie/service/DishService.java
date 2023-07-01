package com.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;
import com.reggie.vo.DishVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 菜品管理
 */
public interface DishService extends IService<Dish> {

    //新增菜品信息
    R<String> addDish(DishDto dishDto);

    //菜品信息分页查询
    R<Page> page(int page, int pageSize, String name);

    //根据菜品id查询对应的菜品信息和口味信息
    DishVo getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    void updateWithFlavor(DishDto dishDto);

    //菜品停售
    R<String> dishUpdateStatus0(Long ids);

    //菜品启售
    R<String> dishUpdateStatus1(Long ids);

    //批量停售
    R<String> batchUpdateStatus0(@RequestParam List<Long> ids);

    //批量启售
    R<String> batchUpdateStatus1(List<Long> ids);

    //删除菜品
    R<String> deleteDish(List<Long> ids);
}