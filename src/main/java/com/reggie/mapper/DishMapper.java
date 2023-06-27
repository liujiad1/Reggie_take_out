package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DishMapper extends BaseMapper<Dish> {

    //菜品停售
    void updateStatus(Long ids,int status);

    void batchUpdateStatus1(int status, List<Long> ids);
}