package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.R;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.entity.Setmeal;
import com.reggie.exception.CustomException;
import com.reggie.mapper.CategoryMapper;
import com.reggie.mapper.DishMapper;
import com.reggie.mapper.SetmealMapper;
import com.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

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


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
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


    /**
     * 根据id删除分类套餐信息
     * 如果当前套餐信息包含了菜品则不能删除
     * @param id
     * @return
     */
    @Override
    public R<String> deleteById(Long id) {

        //根据分类id查询菜品表
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(id != 0,Dish::getCategoryId,id);
        int dishes = dishMapper.selectCount(lqw);
        //关联了菜品信息 不能删除
        if(dishes > 0){
            throw new CustomException("当前分类下存在菜品信息，请先删除菜品再删除分类！");
        }

        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealMapper.selectCount(setmealLambdaQueryWrapper);
        if(count2 > 0){
            throw new CustomException("当前分类下关联了套餐，不能删除");//已经关联套餐，抛出一个业务异常
        }

        //都不存在 正常删除
        categoryMapper.deleteById(id);
        return R.success("删除成功!");
    }

    /**
     * 根据分类表的 类型 查询出所有菜品信息
     * @param type
     * @return
     */
    @Override
    public R<List<Category>> list(int type) {

        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();

        lqw.eq(Category::getType,type).orderByAsc(Category::getSort);

        List<Category> categoryList = categoryMapper.selectList(lqw);


        return R.success(categoryList);
    }
}
