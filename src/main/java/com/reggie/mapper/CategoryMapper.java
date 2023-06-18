package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 分类
 */
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
}
