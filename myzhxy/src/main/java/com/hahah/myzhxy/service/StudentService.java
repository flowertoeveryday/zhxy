package com.hahah.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hahah.myzhxy.pojo.LoginForm;
import com.hahah.myzhxy.pojo.Student;

public interface StudentService extends IService<Student> {
    Student getStudentById(Long userId);

    Student login(LoginForm loginForm);

    IPage<Student> getStudentByOpr(Page<Student> page, Student student);
}
