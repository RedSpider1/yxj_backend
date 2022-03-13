package com.redspider.pss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = {"com.redspider.pss.mapper"})
public class PssApplication {

    public static void main(String[] args) {
        SpringApplication.run(PssApplication.class, args);
    }

}
