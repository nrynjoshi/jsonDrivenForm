package com.jsondriventemplate.app;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.repo.DBConstant;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.jsondriventemplate")
public class JSONDrivenTemplateApplication implements CommandLineRunner {

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
                user.put("username","superadmin");
                user.put("password",AppInject.passwordEncoder.encode("superadmin123"));
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
                loginTemplateInformation.put("json","{ \"definitions\":{ \"page\":{ \"title\": \"JSON Template Builder Login\", \"layout\": \"layout.json\", \"snippet\":\"div.container>div.row.center-content>div.card.min-vw-70>div.card-body>h5.card-title.text-center[Login Page],p.card-text\", \"elements\": [ { \"$ref\": \"#/definitions/login-form\" } ] }, \"login-form\":{ \"definitions\":{ \"type\":\"form\", \"method\":\"POST\", \"action\":\"/login\", \"class\":\"\", \"id\":\"\", \"fields\":{ \"username\":{ \"type\":\"text\", \"label\":\"Username\", \"placeholder\":\"Enter your username\", \"snippet\":\"div.form-group\", \"value\":\"\", \"gridindex\":\"1,1\", \"jsvalidation\":\"\", \"submittransform\":\"\", \"icon\":\"\", \"required\":true, \"class\": \"form-control\", \"id\": \"\" }, \"password\":{ \"type\":\"password\", \"label\":\"Password\", \"placeholder\":\"Enter your password\", \"snippet\":\"div.form-group\", \"value\":\"\", \"gridindex\":\"1,2\", \"jsvalidation\":\"\", \"submittransform\":\"\", \"icon\":\"\", \"required\":true, \"class\": \"form-control\", \"id\": \"\" },  \"create\":{ \"type\":\"submit\", \"label\":\"Login\", \"value\":\"\", \"gridindex\":\"2,1\", \"jsvalidation\":\"\", \"submittransform\":\"\", \"class\":\"btn btn-primary btn-block\" }  } } } } }");
                AppInject.mongoClientProvider.save(loginTemplateInformation, DBConstant.JSON_TEMPLATE_DEFINITION);
            }
        }



    }

}
