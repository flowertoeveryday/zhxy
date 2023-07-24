package com.hahah.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hahah.myzhxy.pojo.Teacher;
import com.hahah.myzhxy.service.TeacherService;
import com.hahah.myzhxy.util.MD5;
import com.hahah.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("增加或修改老师信息")
    @GetMapping("/getTeachers/{pageNo}/[pageSize]")
    public Result getTeachers(
            @PathVariable Integer pageNo,
            @PathVariable Integer pageSize,
            Teacher teacher
    ){
        Page<Teacher> page=new Page<>(pageNo,pageSize);
        IPage<Teacher> iPage =teacherService.getTeacherByOpr(page,teacher);

        return Result.ok(iPage);

    }

    @ApiOperation("")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @RequestBody Teacher teacher
    ){
        Integer id= new Integer(teacher.getId());
        if(id==null||id==0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @ApiOperation("")
    @DeleteMapping("/deletTeacher")
    public Result deletTeacher(
            @RequestBody List<Integer> ids
    ){
        teacherService.removeByIds(ids);
        return Result.ok("删除完成");
    }


}
