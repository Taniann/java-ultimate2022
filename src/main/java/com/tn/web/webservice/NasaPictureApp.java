package com.tn.web.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.tn.web.webservice")
public class NasaPictureApp {

    public static void main(String[] args) {
        SpringApplication.run(NasaPictureApp.class, args);
    }
}
