package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.execptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zouxuyin
 * @since 2020-12-12
 */
@Api(description = "邹绪银的管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {


    //访问地址：http://localhost:8001/eduservice/teacher/findAll
    //http://localhost:8001/swagger-ui.html


    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    //1、查询讲师中的所有信息
    //rest风格
    @ApiOperation(value = "Sr.zou查询所以讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //2、逻辑删除功能
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")  //id是通过路径传递参数值 通过@PathVariable
    public  R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)@PathVariable String id){
        boolean b = teacherService.removeById(id);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }
    /*
    * 如何测试
    * 借助一些工具测试
    * 1、swagger测试   1生产在线接口文档 2方便接口测试
    *   访问地址是固定的：http://localhost:8001/swagger-ui.html
    * 2、postman
    * */


    //3、分页查询
    //current当前页，limit每页值
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //处理异常
        try{
            int i =10/0;
        }catch (Exception e){
            throw  new GuliException(20001,"执行了自定义处理。。。。");
        }

        //调用方法实现分页，
        //调用方法时候，底层封装，把每页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);

        //return  R.ok().data("total",total).data("rows",records);
    }

    //多条件组合查询带分页

    @ApiOperation(value = "多条件组合查询带分页")
    @CrossOrigin
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) TeacherQuery teacherQuery){

        //创建一个page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件条件查询
        //mybatis 学过 动态sql

        ///判断条件是否为空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);//大于等于
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);//小于等于
        }

        //排序
        wrapper.orderByDesc("gmt_create");


        //调用方法实现查询分页

        teacherService.page(pageTeacher,wrapper);
        long total  =pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);

    }

    //添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }


    //根据id查询
    @GetMapping("getTeacher/{id}")
    public  R getTeacher(@PathVariable String id){
        EduTeacher byId = teacherService.getById(id);
        return R.ok().data("teacher",byId);
    }

    //修改讲师接口的方法
    @PostMapping("updateTeacher")

    public R updataTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = teacherService.updateById(eduTeacher);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }



}

