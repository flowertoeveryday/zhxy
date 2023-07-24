package com.hahah.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hahah.myzhxy.pojo.Admin;
import com.hahah.myzhxy.service.AdminService;
import com.hahah.myzhxy.util.MD5;
import com.hahah.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController //不是@Controller 异步交互里面每一个方法都要加上@ResponseBody 我们为了省事就直接把类里面所有方法都变成
                //异步交互的方法 直接返回数据
@RequestMapping("/sms/adminController")   //请求路径  因为前端内容已经写好了adminController 不能更改
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("获得管理员页码和页数的信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("页码") @PathVariable Integer pageNo,
            @ApiParam("页数") @PathVariable Integer pageSize,
            @ApiParam("管理员名字") String admin
    ) {
        Page<Admin> page = new Page<>(pageNo, pageSize);
        IPage<Admin> ipage = adminService.getAdminByOpr(page, admin);

        return Result.ok(ipage);
    }

    @ApiOperation("正价或修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("JSON格式的Admin对象") @RequestBody Admin admin) {
        Integer id = admin.getId();
        if (id == null || id == 0) {

            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @ApiOperation("删除单个或多个admin信息")
    @DeleteMapping("/deletAdmin")
    public Result deletAdmin(
          @ApiParam("要删除单个或多个Admin信息") @RequestBody List<Integer> ids
    ){
        adminService.removeByIds(ids);

         return Result.ok();
    }



}
