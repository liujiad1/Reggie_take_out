package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类
 */
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> findAll(int type);
}
