package com.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.R;
import com.reggie.entity.Category;
import com.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody Category category){
        return categoryService.add(category);
    }

    /**
     * 分类列表 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageSelect(int page,int pageSize){
        return categoryService.pageSelect(page,pageSize);
    }


    /**
     * 根据ID删除分类套餐信息
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> deleteById(Long id){
        return categoryService.deleteById(id);
    }


}
