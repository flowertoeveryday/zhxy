package com.hahah.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hahah.myzhxy.pojo.Clazz;
import com.hahah.myzhxy.service.ClazzService;
import com.hahah.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @ApiOperation("查询所有班级信息")
    @GetMapping("getClazz")
    public Result getCLazz(){
        List<Clazz> clazzes=clazzService.getClazz();
       return Result.ok(clazzes);
    }

    @ApiOperation("删除单个或多个班级")
    @DeleteMapping("deletClazz")
    public Result deletClazz(@ApiParam("要删除的ID JSON数组")@RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return  Result.ok();


    }

    @ApiOperation("增加或者修改班级信息")
    @PostMapping("saveOrUpadteClazz")
    public Result saveOrUpdateClazz(
            @ApiParam("json格式的班级信息")    @RequestBody Clazz clazz
    ){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();


    }

    @ApiOperation("分页带条件模糊查询班级信息")
    @GetMapping("/getClazzByOpr/{pageNo}/{pageSize}")
    public Result getClazzByOpr(
           @ApiParam("分页查询页码数") @PathVariable("pageNo") Integer pageNo,
           @ApiParam("分页查询的页大小")@PathVariable("pageSize") Integer pageSize,
           @ApiParam("分页查询的查询条件") Clazz clazz
    ){
        Page<Clazz> page= new Page<>(pageNo,pageSize);
        IPage<Clazz> ipage = clazzService.getClazzByOpr(page,clazz);
        return Result.ok(ipage);

    }
}
