package com.hahah.myzhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahah.myzhxy.pojo.Admin;
import org.springframework.stereotype.Repository;

@Repository  //让spring识别扫描到这个接口
public interface AdminMapper extends BaseMapper<Admin> /* 接口中定义了一些基本的增删改查方法用的时候直接调用就行了 */ {

}
