package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.R;
import com.reggie.entity.Employee;
import com.reggie.mapper.EmployeeMapper;
import com.reggie.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 员工登录
     * @param httpServletRequest
     * @param employee
     * @return
     */
    @Override
    public R<Employee> login(HttpServletRequest httpServletRequest, Employee employee) {

        //将页面传递的密码进行加密处理
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

        //根据用户名查询用户是否存在
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(employee.getName() != null,Employee::getName,employee.getName());
        Employee emp = employeeMapper.selectOne(lqw);

        //用户为空直接返回
        if(emp == null){
            return R.error("用户不存在！");
        }

        //密码不一致直接返回
        if(!password.equals(emp.getPassword())){
            return R.error("密码错误！");
        }

        //用户名存在且密码正确，判断是否是处于禁用状态
        if(emp.getStatus() == 0){
            return R.error("当前用户处于禁用状态，请联系管理员！");
        }


        //登录成功，将员工id存入session
        httpServletRequest.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }


    /**
     * 员工新增
     * @param httpServletRequest
     * @param employee
     * @return
     */
    @Override
    public R<String> add(HttpServletRequest httpServletRequest,Employee employee) {

        log.info("新增员工信息{}：",employee.toString());

        //设置初始值密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获得当前登录用户的id
        Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);


        employeeMapper.insert(employee);
        return R.success("新增成功！");
    }

    /**
     * 员工分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<Page> page(int page, int pageSize, String name) {

        //创建条件构造器
        Page pageinfo = new Page(page,pageSize);

        //构造条件
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        //  like %name%
        lqw.like(StringUtils.isNotEmpty(name),Employee::getName,name);

        //分页查询
        Page selectPage = employeeMapper.selectPage(pageinfo, lqw);

        return R.success(selectPage);
    }


    /**
     * 修改员工状态
     * @param request
     * @param employee
     * @return
     */
    @Override
    public R<String> updateEmployeeStatus(HttpServletRequest request,@RequestBody Employee employee) {
        log.info(employee.toString());

        Long empId = (Long)request.getSession().getAttribute("employee");

        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeMapper.updateById(employee);

        return R.success("员工信息修改成功");
    }


    /**
     * 根据ID查询员工信息
     * @param id
     * @return
     */
    @Override
    public R<Employee> selectById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        return R.success(employee);
    }

    /**
     * 修改员工信息
     * @param request
     * @param employee
     * @return
     */
    @Override
    public R<String> updateEmployee(HttpServletRequest request,Employee employee) {
        log.info(employee.toString());

        Long empId = (Long)request.getSession().getAttribute("employee");

        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeMapper.updateById(employee);

        return R.success("员工信息修改成功");
    }
}
