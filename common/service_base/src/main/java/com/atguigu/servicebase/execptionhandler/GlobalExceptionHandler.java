package com.atguigu.servicebase.execptionhandler;


import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指出出现什么异常执行方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return  R.error().message("处理全局异常...");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody   //返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return  R.error().message("处理特定异常...");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody   //返回数据
    public R error(GuliException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return  R.error().code(e.getCode()).message(e.getMsg());
    }


}
