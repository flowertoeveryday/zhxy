package com.hahah.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hahah.myzhxy.pojo.Grade;
import com.hahah.myzhxy.service.GradeService;
import com.hahah.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;



    @ApiOperation("获取全部年级")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }

    @ApiOperation("删除年级信息")
    @DeleteMapping("deletGrade")
    public Result deletGrade(@ApiParam("要删除的所有Grade的id的JSON集合") @RequestBody List<Integer> ids){
         gradeService.removeByIds(ids);
         return  Result.ok();
    }

    @ApiOperation("保存新增或修改grade")
    @PostMapping("saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam ("JSON的Grade对象有ID是修改没有是增加")@RequestBody  Grade grade){


        //调用服务层方法完成增减或修改
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("根据年级，分页模糊查询")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
           @ApiParam("页码")  @PathVariable("pageNo") Integer pageNo,
           @ApiParam("页数")  @PathVariable("pageSize") Integer pageSize,
           @ApiParam("分页查询模糊匹配的名称") String gradeName

    ){
        // 分页 带条件查询
        Page<Grade> page =new Page<>(pageNo,pageSize);
        // 通过服务层
        IPage<Grade> pageRs=gradeService.getGradeByOpr(page,gradeName);

        // 封装Result对象并返回
        return Result.ok(pageRs);
    }
}


