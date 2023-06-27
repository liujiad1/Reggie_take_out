package com.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import com.reggie.vo.DishVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品信息
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto){
        return dishService.addDish(dishDto);
    }


    /**
     * 菜品分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        return dishService.page(page,pageSize,name);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishVo> get(@PathVariable Long id){
        DishVo dishVo = dishService.getByIdWithFlavor(id);
        return R.success(dishVo);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

//    /**
//     * 菜品停售 status = 0
//     * @param ids
//     * @return
//     */
//    @PostMapping("/status/0")
//    public R<String> dishUpdateStatus0(Long ids){
//        return dishService.dishUpdateStatus0(ids);
//    }
//
//
//    /**
//     * 菜品启售 status = 1
//     * @param ids
//     * @return
//     */
//    @PostMapping("/status/0")
//    public R<String> dishUpdateStatus1(Long ids){
//        return dishService.dishUpdateStatus1(ids);
//    }


    /**
     * 批量停售
     * @param ids
     * @return
     */
    @PostMapping("/status/0")
    public R<String> batchUpdateStatus0(@RequestParam List<Long> ids){
        return dishService.batchUpdateStatus0(ids);
    }


    /**
     * 批量启售
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    public R<String> batchUpdateStatus1(@RequestParam List<Long> ids){
        return dishService.batchUpdateStatus1(ids);
    }





}    