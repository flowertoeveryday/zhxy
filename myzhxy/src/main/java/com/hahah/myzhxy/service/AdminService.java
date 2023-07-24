package com.hahah.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hahah.myzhxy.pojo.Admin;
import com.hahah.myzhxy.pojo.LoginForm;

public interface AdminService extends IService<Admin>{  /* mybatis中要想使用mybatisplus 要求在service层
                                                           继承或实现一个IService 的接口 这个接口里面定义了一些基本的
                                                          增删改查的业务方法*/
    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);


    IPage<Admin> getAdminByOpr(Page<Admin> page, String admin);
}
