package com.reggie.exception;

/**
 * 自定义业务异常类
 *      分类关联异常
 */
public class CustomException extends RuntimeException {

    public CustomException(String message){
        super(message);
    }

}