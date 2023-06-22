package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.entity.DishFlavor;
import com.reggie.entity.Employee;
import com.reggie.mapper.CategoryMapper;
import com.reggie.mapper.DishFlavorMapper;
import com.reggie.mapper.DishMapper;
import com.reggie.service.DishService;
import com.reggie.vo.DishVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 菜品管理
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增菜品信息
     * @param dishDto
     * @return
     */
    @Transactional
    @Override
    public R<String> addDish(DishDto dishDto) {
        log.info("前端菜品参数：{}",dishDto);

        //添加菜品表基本信息
        dishMapper.insert(dishDto);
        //获取到新增ID
        Long id = dishDto.getId();
        //根据ID插入菜品口味信息到口味表中
        List<DishFlavor> flavors = dishDto.getFlavors();

        for(DishFlavor dishFlavor : flavors){
            dishFlavor.setDishId(id);
            //口味信息日志信息
            log.info("口味信息：{}",dishFlavor);
            dishFlavorMapper.insert(dishFlavor);
        }
        return R.success("新增成功！");
    }

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<Page> page(int page, int pageSize, String name) {

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishVo> dishVoPage = new Page<>();

        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //添加过滤条件
        lqw.like(name != null,Dish::getName,name);
        //添加排序条件
        lqw.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishMapper.selectPage(pageInfo,lqw);


        //属性拷贝 第三个参数为忽略掉 records 属性集合的拷贝
        BeanUtils.copyProperties(pageInfo,dishVoPage,"records");
        List<Dish> dishRecords = pageInfo.getRecords();
        List<DishVo> dvRecords = dishVoPage.getRecords();

        ArrayList<DishVo> dishVos = new ArrayList<>();
        BeanUtils.copyProperties(dvRecords,dishVos);

        for(Dish dish : dishRecords){
            DishVo dishVo = new DishVo();
            BeanUtils.copyProperties(dish,dishVo);

            Long categoryId = dish.getCategoryId();

            Category category = categoryMapper.selectById(categoryId);

            if(category != null){
                dishVo.setCategoryName(category.getName());
            }

            dishVos.add(dishVo);
        }

        dishVoPage.setRecords(dishVos);


        return R.success(dishVoPage);
    }
}