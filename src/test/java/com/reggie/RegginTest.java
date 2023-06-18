package com.reggie;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.reggie.entity.Employee;
import com.reggie.mapper.EmployeeMapper;
import com.reggie.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class RegginTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void test(){
        Employee employee = new Employee();
        employee.setUsername("test5");
        employee.setName("test10");
        employee.setPassword("123456");
        employee.setPhone("17673496515");
        employee.setIdNumber("430623200107018336");
        employee.setCreateTime(LocalDateTime.now());
        employee.setSex("1");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(1L);
        employee.setUpdateUser(1L);

        employeeMapper.insert(employee);
        System.out.println(employee.getId());


    }

//    @Test
//    public void test2(){
//        employeeService.updateEmployeeStatus(2,1);
//    }

}
