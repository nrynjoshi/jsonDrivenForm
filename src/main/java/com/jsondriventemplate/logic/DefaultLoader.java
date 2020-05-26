package com.jsondriventemplate.logic;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import com.jsondriventemplate.repo.DBConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public final class DefaultLoader {

    @Value("${default.username}")
    private String userName;
    @Value("${default.password}")
    private String password;
    @Value("${database.createupdate}")
    private String createUpdate;

    public void defaultJSONLoader() throws IOException {
        loadDefaultJSON("login", "Login Page", "login.json");
        loadDefaultJSON("dashboard", "Dashboard", "dashboard.json");
        loadDefaultJSON("employee", "Employee", "employee.json");
        loadDefaultJSON("auth-dashboard", "Employee Dashboard", "auth-dashboard.json");
    }

    public void createUpdateDefaultJSON() throws IllegalAccessException {
        //create necessary collection if not exist
        if(createUpdate.equalsIgnoreCase("create")){
            Field[] fields = DBConstant.class.getDeclaredFields();
            for (Field field : fields) {
                String value = (String) field.get(new DBConstant());
                AppInject.mongoClientProvider.dropCollection(value);
            }
        }
        AppInject.mongoClientProvider.collection(DBConstant.TEMPLATE_INFORMATION);
        AppInject.mongoClientProvider.collection(DBConstant.JSON_TEMPLATE_DEFINITION);
    }

    public void loadDefaultUser() {
        Map login = AppInject.mongoClientProvider.findByAtt("username","superadmin", DBConstant.EMPLOYEE);
        if(login==null || StringUtils.isBlank((CharSequence) login.get("_id"))){
            Map<String,Object> user=new HashMap<>();
            user.put("username",userName);
            user.put("password",AppInject.passwordEncoder.encode(password));
            user.put("fullname","Super User");
            user.put("role","Super_Admin");
            AppInject.mongoClientProvider.save(user, DBConstant.EMPLOYEE);
        }
    }

    private void loadDefaultJSON(String url, String name, String fileName) throws IOException {
        {
            Map login = AppInject.mongoClientProvider.findByURL(url, DBConstant.TEMPLATE_INFORMATION);
            if (login == null || StringUtils.isBlank((CharSequence) login.get("url"))) {
                Map<String, Object> loginTemplateInformation = new HashMap<>();
                loginTemplateInformation.put("url", url);
                loginTemplateInformation.put("name", name);
                AppInject.mongoClientProvider.save(loginTemplateInformation, DBConstant.TEMPLATE_INFORMATION);
            }
        }
        {
            Map login = AppInject.mongoClientProvider.findByURL(url, DBConstant.TEMPLATE_INFORMATION);
            Map templateDef = AppInject.mongoClientProvider.findById((String) login.get("_id"), DBConstant.JSON_TEMPLATE_DEFINITION);
            if (templateDef == null || StringUtils.isBlank((CharSequence) templateDef.get("json"))) {
                Map<String, Object> loginTemplateInformation = new HashMap<>();
                loginTemplateInformation.put("_id", login.get("_id"));
                File file = ResourceUtils.getFile("classpath:" + JSONTemplateConst.JSON_SCHEMA_ATTR + fileName);
                loginTemplateInformation.put("json", JSONLoader.laodJSONDefinition(file));
                AppInject.mongoClientProvider.save(loginTemplateInformation, DBConstant.JSON_TEMPLATE_DEFINITION);
            }
        }
    }

}
