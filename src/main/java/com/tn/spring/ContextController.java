package com.tn.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/spring")
public class ContextController {

    @Autowired
    ApplicationContext applicationContext;

    @GetMapping("/beans")
    public List<String> getAllSpringBeans() {
        return Arrays.stream(applicationContext.getBeanDefinitionNames())
                     .toList();
    }
}
