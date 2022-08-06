package com.activiti6;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.activiti6.mapper")
@EnableScheduling
public class Activiti6DemoApplication {

    public static void main_(String[] args) {
        SpringApplication.run(Activiti6DemoApplication.class, args);
    }

}
