package com.reggie;

import com.reggie.entity.Category;
import com.reggie.entity.Employee;
import com.reggie.mapper.CategoryMapper;
import com.reggie.mapper.EmployeeMapper;
import com.reggie.service.CategoryService;
import com.reggie.service.DishService;
import com.reggie.service.EmployeeService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class RegginTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;


    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void test(){
        Employee employee = new Employee();
        employee.setUsername("test5");
        employee.setName("test10");
        employee.setPassword("123456");
        employee.setPhone("17673496515");
        employee.setIdNumber("430623200107018336");
        employee.setSex("1");
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser(1L);
//        employee.setUpdateUser(1L);

        employeeMapper.insert(employee);
        System.out.println(employee.getId());


    }

    @Test
    public void categoryList(){
        int type = 1;
//        R<List<Category>> list = categoryService.list(type);
//        System.out.println(list.toString());

        List<Category> all = categoryMapper.findAll(type);
        System.out.println(all);

    }





    /**
     * 新增菜品测试
     */
//    @Test
//    public void addDishTest(){
//
//    }





}
