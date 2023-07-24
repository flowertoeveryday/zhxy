package com.hahah.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data

@TableName("tb_clazz")
public class Clazz {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String name;
    private Integer number;
    private String introducation;
    private  String headmaster;
    private  String email;
    private  String telephone;
    private  String gradeName;

}
