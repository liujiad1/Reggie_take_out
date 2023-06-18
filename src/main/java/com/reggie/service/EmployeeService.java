package com.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.common.R;
import com.reggie.entity.Employee;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 员工处理接口
 */
public interface EmployeeService extends IService<Employee> {

    // 员工登录
    R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee);

    //新增员工
    R<String> add(HttpServletRequest httpServletRequest,@RequestBody Employee employee);

    //员工分页查询
    R<Page> page(int page, int pageSize, String name);

    //修改员工状态信息
    R<String> updateEmployeeStatus(HttpServletRequest request,@RequestBody Employee employee);

    //根据id查询员工信息
    R<Employee> selectById(@PathVariable Long id);

    //修改员工信息
    R<String> updateEmployee(HttpServletRequest request,@RequestBody Employee employee);
}
