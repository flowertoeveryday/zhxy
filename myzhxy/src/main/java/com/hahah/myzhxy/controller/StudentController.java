package com.hahah.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hahah.myzhxy.pojo.Student;
import com.hahah.myzhxy.service.StudentService;
import com.hahah.myzhxy.util.MD5;
import com.hahah.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @DeleteMapping("/delStudentById")
    public Result delStudentById(
            @RequestBody List<Integer> ids
            ){
        studentService.removeByIds(ids);
        return  Result.ok();
    }

    @PostMapping("/addOrUpdateStudent")
   public Result addOrUpdateStudent(
           @RequestBody Student student
    ){
         Integer id=student.getId();
         if(null==id||1==id){
             student.setPassword(MD5.encrypt(student.getPassword()));
         }
         studentService.saveOrUpdate(student);
         return Result.ok();

    }

    @ApiOperation("根据学生分页模糊查询")
    @GetMapping("getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
           @ApiParam("页数") @PathVariable Integer pageNo,
           @ApiParam("页大小") @PathVariable Integer pageSize,
           @ApiParam("查询条件") Student student
            ){
        Page<Student> pageParam  =new Page<>(pageNo,pageSize) ;
        IPage<Student> ipage = studentService.getStudentByOpr(pageParam,student);
        return Result.ok(ipage);


    }
}
