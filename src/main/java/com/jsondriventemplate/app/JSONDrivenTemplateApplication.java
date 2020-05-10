package com.jsondriventemplate.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.jsondriventemplate")
public class JSONDrivenTemplateApplication {

    @PostConstruct
    public void initApp(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(JSONDrivenTemplateApplication.class, args);
    }

}
