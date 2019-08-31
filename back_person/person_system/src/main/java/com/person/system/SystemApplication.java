package com.person.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: pzy
 * @create: 2019/8/31 17:48
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(value = {"com.person", "com.person.auth"})
@MapperScan({"com.person", "com.person.auth"})
public class SystemApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SystemApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }


}
