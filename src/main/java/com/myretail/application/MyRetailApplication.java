package com.myretail.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com/myretail"})
public class MyRetailApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyRetailApplication.class, args);
    }

}
