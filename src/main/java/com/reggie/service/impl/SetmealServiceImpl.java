//package com.reggie.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.reggie.common.R;
//import com.reggie.dto.SetmealDto;
//import com.reggie.entity.Category;
//import com.reggie.entity.Setmeal;
//import com.reggie.entity.SetmealDish;
//import com.reggie.mapper.CategoryMapper;
//import com.reggie.mapper.SetmealDishMapper;
//import com.reggie.mapper.SetmealMapper;
//import com.reggie.service.SetmealDishService;
//import com.reggie.service.SetmealService;
//import com.reggie.vo.SetmealVo;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 套餐管理
// */
//@Service
//@Slf4j
//public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {
//
//    @Autowired
//    private SetmealMapper setmealMapper;
//    @Autowired
//    private SetmealDishMapper setmealDishMapper;
//    @Autowired
//    private SetmealDishService setmealDishService;
//
//    @Autowired
//    private CategoryMapper categoryMapper;
//
//    /**
//     * 新增套餐
//     * @param setmealDto
//     */
//    @Override
//    public void saveWithDish(SetmealDto setmealDto) {
//
//        //保存套餐基本信息
//        setmealMapper.insert(setmealDto);
//
//        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
//
//        //设置套餐菜品信息表的 套餐ID
//        for(SetmealDish setmealDish : setmealDishes){
//            setmealDish.setSetmealId(setmealDto.getId());
//        }
//
//        //批量保存
//        setmealDishService.saveBatch(setmealDishes);
//
//    }
//
//    @Override
//    public R<Page> page(int page, int pageSize, String name) {
//
//        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
//        Page<SetmealVo> setmealVoPage = new Page<>();
//
//        //套餐表基础查询
//        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
//        lqw.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
//        setmealMapper.selectPage(setmealPage,lqw);
//
//        //对象拷贝 排除records列表数据
//        BeanUtils.copyProperties(setmealPage,setmealVoPage,"records");
//
//        //套餐基础信息
//        List<Setmeal> setmealList = setmealPage.getRecords();
//
//        //VO集合
//        ArrayList<SetmealVo> setmealVos = new ArrayList<>();
//
//        for(Setmeal setmeal : setmealList){
//            SetmealVo setmealVo = new SetmealVo();
//            BeanUtils.copyProperties(setmeal,setmealVo);
//
//            //分类对象
//            Category category = categoryMapper.selectById(setmeal.getCategoryId());
//
//            if(category != null){
//                setmealVo.setCategoryName(category.getName());
//            }
//
//            setmealVos.add(setmealVo);
//        }
//
//        //设置分页数据集合
//        setmealVoPage.setRecords(setmealVos);
//
//        return R.success(setmealVoPage);
//    }
//
//    /**
//     * 停用套餐
//     * @param ids
//     * @return
//     */
//    @Override
//    public R<String> updateStatus(Long ids) {
//        Setmeal setmeal = setmealMapper.selectById(ids);
//        setmeal.setStatus(0);
//
//        setmealMapper.updateById(setmeal);
//
//        return R.success("修改成功！");
//    }
//
////    /**
////     * 批量启动套餐
////     * @param ids
////     * @return
////     */
////    @Override
////    public R<String> updateStatusList(List<Long> ids) {
////
////        log.info("ids{}",ids.toString());
////
////        //根据id 查询套餐信息
////        List<Setmeal> setmeals = setmealMapper.selectBatchIds(ids);
////        //将所有套餐信息状态更改为启用状态
////        for (Setmeal setmeal : setmeals){
////            setmeal.setStatus(1);
////        }
////        log.info("套餐信息{}",setmeals.toString());
////
////        //批量修改
////        this.updateBatchById(setmeals);
////        return R.success("修改成功！");
////    }
//
//    /**
//     * SQL模式
//     * 、批量启动套餐
//     * @param ids
//     * @return
//     */
//    @Override
//    public R<String> updateStatusList2(List<Long> ids) {
//        int status = 1;
//        setmealMapper.updateStatus(ids,status);
//        return R.success("修改成功！");
//    }
//}