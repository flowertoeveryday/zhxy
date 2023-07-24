package com.hahah.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahah.myzhxy.mapper.TeacherMapper;
import com.hahah.myzhxy.pojo.LoginForm;
import com.hahah.myzhxy.pojo.Teacher;
import com.hahah.myzhxy.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password",loginForm.getPassword());
        Teacher teacher= baseMapper.selectOne(queryWrapper);
        return  teacher;
    }

    
    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("id",userId);

        return baseMapper.selectOne(queryWrapper);

    }

    @Override
    public IPage<Teacher> getTeacherByOpr(Page<Teacher> pageParam, Teacher teacher) {
       QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
       if(!StringUtils.isEmpty(teacher.getName())){
           queryWrapper.like("name",teacher.getName());
       }
       if(!StringUtils.isEmpty(teacher.getClazzName())){
           queryWrapper.like("clazz_name",teacher.getClazzName());
       }
       queryWrapper.orderByDesc("id");
       Page page=baseMapper.selectPage(pageParam,queryWrapper);
       return page;
    }
}
