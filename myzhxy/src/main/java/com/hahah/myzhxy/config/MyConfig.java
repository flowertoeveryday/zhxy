package com.hahah.myzhxy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.hahah.myzhxy.mapper")
public class MyConfig {

    @Bean
    public PaginationInterceptor PaginationInterceptor(){
         PaginationInterceptor paginationInterceptor=new PaginationInterceptor();
         //paginationInterceptor.setLimit(你的最大单页限制数量，默认为500条，小于0时，如-1则为不受限制);
         return paginationInterceptor;

    }


}
