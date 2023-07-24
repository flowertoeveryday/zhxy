package com.hahah.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data

@TableName("db_grade")
public class Grade {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String name;
    private  String manager;
    private  String email;
    private  String telephone;
    private  String introducation;

}
