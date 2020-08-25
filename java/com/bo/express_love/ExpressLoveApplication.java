package com.bo.express_love;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.bo.express_love.repository")
public class ExpressLoveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpressLoveApplication.class, args);
    }

}
