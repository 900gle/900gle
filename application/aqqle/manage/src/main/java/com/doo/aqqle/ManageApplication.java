package com.doo.aqqle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication(scanBasePackages = "com.doo.aqqle")
public class ManageApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }

}
