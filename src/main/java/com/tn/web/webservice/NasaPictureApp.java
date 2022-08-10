package com.tn.web.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages="com.tn.web.webservice")
public class NasaPictureApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NasaPictureApp.class, args);
        context.getBean("");
    }
}
