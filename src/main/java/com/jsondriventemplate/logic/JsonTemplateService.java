package com.jsondriventemplate.logic;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.exception.URINotFoundException;
import com.jsondriventemplate.repo.DBConstant;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonTemplateService {
    public Map getJSONFromURIEditorView(String uri) throws Exception {
        try {
            return getJSONFromURI(uri);
        } catch (URINotFoundException e) {

            return null;

        }
    }

    public String getJSONOnlyFromURI(String uri) throws Exception {
       return (String) getJSONFromURI(uri).get("json");
    }

    public Map getJSONFromURI(String uri) throws Exception {
        AppInject.templateParser.validateURIJSON(uri);
        Map byURL = AppInject.mongoClientProvider.findByURL(uri, DBConstant.TEMPLATE_INFORMATION);
        Map byAtt = AppInject.mongoClientProvider.findByAtt("_id", (String) byURL.get("_id"), DBConstant.JSON_TEMPLATE_DEFINITION);
        if (byAtt == null) {
            throw new URINotFoundException("JSON definition is not found for particular page");
        }
        return byAtt;
    }

}
