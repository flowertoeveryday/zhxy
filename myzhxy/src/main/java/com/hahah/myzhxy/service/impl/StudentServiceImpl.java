package com.hahah.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahah.myzhxy.mapper.StudentMapper;
import com.hahah.myzhxy.pojo.Admin;
import com.hahah.myzhxy.pojo.LoginForm;
import com.hahah.myzhxy.pojo.Student;
import com.hahah.myzhxy.pojo.Teacher;
import com.hahah.myzhxy.service.StudentService;
import com.hahah.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student getStudentById( Long userId) {
        QueryWrapper<Student> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student =baseMapper.selectOne(queryWrapper);
        return student;


    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> pageParam, Student student) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(student)){
         queryWrapper.like("namae",student.getName());
        }
        if(!StringUtils.isEmpty(student.getClazzName())){
            queryWrapper.like("clazz_name",student.getClazzName());
        }
        queryWrapper.orderByDesc("id");
        Page<Student> page = baseMapper.selectPage(pageParam, queryWrapper);
        return page;

    }
}
