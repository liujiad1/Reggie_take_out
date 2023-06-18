package com.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.R;
import com.reggie.entity.Employee;
import com.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param httpServletRequest
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee){
        return employeeService.login(httpServletRequest,employee);
    }


    /**
     *  退出登录，清除 Session
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest){
        //清理session
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success("退出成功!");
    }

    /**
     * 新增员工
     * @param httpServletRequest
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> add(HttpServletRequest httpServletRequest,@RequestBody Employee employee){

        return employeeService.add(httpServletRequest,employee);

    }


    /**
     * 员工分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        return employeeService.page(page,pageSize,name);
    }

//    /**
//     * 修改员工状态信息
//     * @param request
//     * @param employee
//     * @return
//     */
//    @PutMapping
//    public R<String> updateEmployeeStatus(HttpServletRequest request,@RequestBody Employee employee){
//        return employeeService.updateEmployeeStatus(request,employee);
//    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        return employeeService.selectById(id);
    }

    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        return employeeService.updateEmployee(request,employee);
    }

}
