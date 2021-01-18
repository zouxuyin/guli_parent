package com.atguigu.eduservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
public class EduAppliction {
    public static void main(String[] args) {
        SpringApplication.run(EduAppliction.class,args);
    }
}



