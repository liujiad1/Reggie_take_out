package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.R;
import com.reggie.entity.Category;
import com.reggie.mapper.CategoryMapper;
import com.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分类
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @Override
    public R<String> add(Category category) {
        categoryMapper.insert(category);
        return R.success("新增分类成功！");
    }


    @Override
    public R<Page> pageSelect(int page, int pageSize) {

        Page<Category> pageInfo = new Page<Category>(page,pageSize);

        //按照序号升序排列
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);

        //分页查询
        Page<Category> categoryPage = categoryMapper.selectPage(pageInfo, lqw);

        return R.success(categoryPage);
    }
}
