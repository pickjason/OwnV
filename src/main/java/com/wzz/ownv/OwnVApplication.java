package com.wzz.ownv;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wzz.ownv.mapper")
public class OwnVApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnVApplication.class, args);
    }

}
