package com.szb.java5;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableAsync
@MapperScan("com.szb.java5.mapper")
@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication
public class Java5Application {

    public static void main(String[] args) {
        SpringApplication.run(Java5Application.class, args);
    }

}
