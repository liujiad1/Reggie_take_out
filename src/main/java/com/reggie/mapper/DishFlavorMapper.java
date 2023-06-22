package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 菜品口味
 */
@Mapper
@Repository
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}