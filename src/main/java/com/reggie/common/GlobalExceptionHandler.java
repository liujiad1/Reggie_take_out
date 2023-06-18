package com.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局异常处理类
 */
@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class}) //拦截类上标注了 该注解的类
public class GlobalExceptionHandler {

    /**
     * 拦截数字异常 --- 测试
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    public R<String> test(ArithmeticException e){
        log.error(e.getMessage());
        return R.error("出错了！");
    }
}
