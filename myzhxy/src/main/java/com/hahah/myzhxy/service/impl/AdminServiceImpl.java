package com.hahah.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahah.myzhxy.mapper.AdminMapper;
import com.hahah.myzhxy.pojo.Admin;
import com.hahah.myzhxy.pojo.LoginForm;
import com.hahah.myzhxy.pojo.Teacher;
import com.hahah.myzhxy.service.AdminService;
import com.hahah.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


//public class AdminServiceImpl implements AdminService 因为AdminService里面继承了 IService 所以会报错
// 因为IService里面有很多抽象方法如果自己实现会很麻烦所以让当前类继承ServiceImpl 它里面包含了IService 的实现

@Service("adminServiceImpl") //让Spring 初始化AdminSerbiceImpl对象  让adminServiceImpl 作为当前实体类的id
@Transactional //事务控制 ，事务回滚
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
                                //数据库中列的名字
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("Password", MD5.encrypt(loginForm.getPassword()));
        Admin admin =baseMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        QueryWrapper<Admin> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("id",userId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Admin> getAdminByOpr(Page<Admin> pageParam, String admin) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(admin)){
         queryWrapper.like("name",admin);
        }
        queryWrapper.orderByDesc("id");
        Page<Admin> page = baseMapper.selectPage(pageParam,queryWrapper);
        return page;


    }


}
