package com.jsondriventemplate.app;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import com.jsondriventemplate.logic.JSONLoader;
import com.jsondriventemplate.repo.DBConstant;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.jsondriventemplate")
public class JSONDrivenTemplateApplication implements CommandLineRunner {

    @Value("${default.username}")
    private String userName;
    @Value("${default.password}")
    private String password;

    @PostConstruct
    public void initApp(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(JSONDrivenTemplateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //create necessary collection if not exist
        AppInject.mongoClientProvider.collection(DBConstant.TEMPLATE_INFORMATION);
        AppInject.mongoClientProvider.collection(DBConstant.JSON_TEMPLATE_DEFINITION);

        {
            Map login = AppInject.mongoClientProvider.findByAtt("username","superadmin", DBConstant.USER);
            if(login==null || StringUtils.isBlank((CharSequence) login.get("_id"))){
                Map<String,String> user=new HashMap<>();
                user.put("username",userName);
                user.put("password",AppInject.passwordEncoder.encode(password));
                user.put("enable","on");
                user.put("fullname","Super User");
                user.put("role","Super_Admin");
                AppInject.mongoClientProvider.save(user, DBConstant.USER);
            }
        }

        {
            Map login = AppInject.mongoClientProvider.findByURL("login", DBConstant.TEMPLATE_INFORMATION);
            if(login==null || StringUtils.isBlank((CharSequence) login.get("url"))){
                Map<String,String> loginTemplateInformation=new HashMap<>();
                loginTemplateInformation.put("url","login");
                loginTemplateInformation.put("name","Login Page");
                AppInject.mongoClientProvider.save(loginTemplateInformation, DBConstant.TEMPLATE_INFORMATION);
            }
        }
        {
            Map login = AppInject.mongoClientProvider.findByURL("login", DBConstant.TEMPLATE_INFORMATION);
            Map templateDef = AppInject.mongoClientProvider.findById((String) login.get("_id"), DBConstant.JSON_TEMPLATE_DEFINITION);
            if(templateDef==null || StringUtils.isBlank((CharSequence) templateDef.get("value"))){
                Map<String,Object> loginTemplateInformation=new HashMap<>();
                loginTemplateInformation.put("_id",  login.get("_id"));
                File file = ResourceUtils.getFile("classpath:"+ JSONTemplateConst.JSON_SCHEMA_ATTR+"login.json");
                loginTemplateInformation.put("json",JSONLoader.laodJSONDefinition(file));
                AppInject.mongoClientProvider.save(loginTemplateInformation, DBConstant.JSON_TEMPLATE_DEFINITION);
            }
        }
        {
            Map login = AppInject.mongoClientProvider.findByURL("dashboard", DBConstant.TEMPLATE_INFORMATION);
            if(login==null || StringUtils.isBlank((CharSequence) login.get("url"))){
                Map<String,String> loginTemplateInformation=new HashMap<>();
                loginTemplateInformation.put("url","dashboard");
                loginTemplateInformation.put("name","Dashboard");
                AppInject.mongoClientProvider.save(loginTemplateInformation, DBConstant.TEMPLATE_INFORMATION);
            }
        }
        {
            Map login = AppInject.mongoClientProvider.findByURL("dashboard", DBConstant.TEMPLATE_INFORMATION);
            Map templateDef = AppInject.mongoClientProvider.findById((String) login.get("_id"), DBConstant.JSON_TEMPLATE_DEFINITION);
            if(templateDef==null || StringUtils.isBlank((CharSequence) templateDef.get("value"))){
                Map<String,Object> loginTemplateInformation=new HashMap<>();
                loginTemplateInformation.put("_id",  login.get("_id"));
                File file = ResourceUtils.getFile("classpath:"+ JSONTemplateConst.JSON_SCHEMA_ATTR+"dashboard.json");
                loginTemplateInformation.put("json",JSONLoader.laodJSONDefinition(file));
                AppInject.mongoClientProvider.save(loginTemplateInformation, DBConstant.JSON_TEMPLATE_DEFINITION);
            }
        }
        {
            Map login = AppInject.mongoClientProvider.findByURL("employee", DBConstant.TEMPLATE_INFORMATION);
            if(login==null || StringUtils.isBlank((CharSequence) login.get("url"))){
                Map<String,String> loginTemplateInformation=new HashMap<>();
                loginTemplateInformation.put("url","employee");
                loginTemplateInformation.put("name","Employee");
                AppInject.mongoClientProvider.save(loginTemplateInformation, DBConstant.TEMPLATE_INFORMATION);
            }
        }
        {
            Map login = AppInject.mongoClientProvider.findByURL("employee", DBConstant.TEMPLATE_INFORMATION);
            Map templateDef = AppInject.mongoClientProvider.findById((String) login.get("_id"), DBConstant.JSON_TEMPLATE_DEFINITION);
            if(templateDef==null || StringUtils.isBlank((CharSequence) templateDef.get("value"))){
                Map<String,Object> loginTemplateInformation=new HashMap<>();
                loginTemplateInformation.put("_id",  login.get("_id"));
                File file = ResourceUtils.getFile("classpath:"+ JSONTemplateConst.JSON_SCHEMA_ATTR+"employee.json");
                loginTemplateInformation.put("json",JSONLoader.laodJSONDefinition(file));
                AppInject.mongoClientProvider.save(loginTemplateInformation, DBConstant.JSON_TEMPLATE_DEFINITION);
            }
        }


    }

}
