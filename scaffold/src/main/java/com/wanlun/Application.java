package com.wanlun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement   //开启事务支持
@SpringBootApplication
@MapperScan({"com.wanlun.service.**.dao"})  //扫描此包下的所有dao
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
