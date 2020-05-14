package com.jsondriventemplate.logic;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.exception.URINotFoundException;
import com.jsondriventemplate.repo.DBConstant;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonTemplateService {

    public String getJSONFromURI(String uri) throws Exception {
        AppInject.templateParser.validateURIJSON(uri);
        Map byURL = AppInject.mongoClientProvider.findByURL(uri, DBConstant.TEMPLATE_INFORMATION);
        Map byAtt = AppInject.mongoClientProvider.findByAtt("_id", (String) byURL.get("_id"), DBConstant.JSON_TEMPLATE_DEFINITION);
        if(byAtt==null){
            throw new URINotFoundException("JSON definition is not found for particular page");
        }
        return (String) byAtt.get("json");
    }

}
