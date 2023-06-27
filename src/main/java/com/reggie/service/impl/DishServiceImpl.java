package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
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
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import com.reggie.vo.DishVo;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private DishFlavorService dishFlavorService;

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
        //添加过滤条件  添加排序条件
        lqw.like(name != null,Dish::getName,name).orderByDesc(Dish::getUpdateTime);

//        lqw.orderByDesc(Dish::getUpdateTime);

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


    /**
     * 根据菜品id查询菜品信息和口味信息
     * @param id
     * @return
     */
    @Override
    public DishVo getByIdWithFlavor(Long id) {

        //菜品基本信息
        Dish dish = dishMapper.selectById(id);
        log.info("菜品基本信息{}",dish);

        //根据id查询口味信息
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dish.getId());

        List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(lqw);
        log.info("菜品口味信息{}",dishFlavors);

        DishVo dishVo = new DishVo();
        BeanUtils.copyProperties(dish,dishVo);
        dishVo.setFlavors(dishFlavors);
        log.info("菜品VO信息{}",dishVo);

        return dishVo;
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新菜品信息 先删除 后添加
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dishDto.getId());
        //删除
        dishFlavorMapper.delete(lqw);

        //批量保存
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        log.info("口味信息{}",dishFlavors);
        //设置口味表的菜品ID
        for (DishFlavor dishFlavor : dishFlavors){
            dishFlavor.setDishId(dishDto.getId());
        }
        //批量保存
        dishFlavorService.saveBatch(dishDto.getFlavors());

        //更新菜品基本信息
        dishMapper.updateById(dishDto);
    }

    /**
     * 菜品停售
     * @param ids
     * @return
     */
    @Override
    public R<String> dishUpdateStatus0(Long ids) {
        int status = 0;
        dishMapper.updateStatus(ids,status);

        return R.success("修改成功！");
    }

    /**
     * 菜品停售
     * @param ids
     * @return
     */
    @Override
    public R<String> dishUpdateStatus1(Long ids) {
        int status = 1;
        dishMapper.updateStatus(ids,status);

        return R.success("修改成功！");
    }


    /**
     * 批量停售
     * @param ids
     * @return
     */
    @Override
    public R<String> batchUpdateStatus0(List<Long> ids) {

        List<Dish> dishes = dishMapper.selectBatchIds(ids);
        for(Dish dish : dishes){
            dish.setStatus(0);
        }

        this.updateBatchById(dishes);

        return R.success("修改成功！");
    }

    @Override
    public R<String> batchUpdateStatus1(List<Long> ids) {
        int status = 1;
        dishMapper.batchUpdateStatus1(status,ids);
        return R.success("修改成功！");
    }
}