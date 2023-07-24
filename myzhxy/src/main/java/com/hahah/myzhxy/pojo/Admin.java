package com.hahah.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //全参构造
@NoArgsConstructor   //无参构造
@TableName("tb_admin")  //指定对应的是那个表
public class Admin {
    @TableId(value="id",type= IdType.AUTO)   //指定主键自增
    private  Integer id;
    private  String name;
    private  Character gender;
    private  String password;
    private  String email;
    private  String telephone;
    private  String address;
    private  String portraitPath;//头像图片路径



}
