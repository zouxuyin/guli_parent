package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TeacherQuery {

    @ApiModelProperty(value = "教师名字,模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2 首席讲师")
    private  Integer level;

    @ApiModelProperty(value = "查询开始时间",example="2020-02-04 10:13:11")
    private String begin;   //这里使用的是String类型，而前端获取过来的数据一般是正确的，所以不需要转型


    @ApiModelProperty(value = "查询开始时间",example="2020-02-04 10:14:11")
    private  String end;

}
