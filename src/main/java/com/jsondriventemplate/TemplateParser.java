package com.jsondriventemplate;

import com.jsondriventemplate.config.MessageReader;
import com.jsondriventemplate.exception.URINotFoundException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateParser {

    @Autowired
    private Configuration configuration;
    @Autowired
    private MessageReader messageReader;

    public void validateURIJSON(String uri) throws Exception {
        if (StringUtils.isBlank(uri)) {
            throw new URINotFoundException(messageReader.get(StatusCode.NOT_FOUND.value()));
        }
        File file = Paths.get("json-schema", uri + ".json").toFile();
        if (!file.exists()) {
            throw new URINotFoundException(messageReader.get(StatusCode.NOT_FOUND.value()));
        }
    }

    public String pageDefinition(File jsonFile) throws IOException, TemplateException {
        String jsonData = JSONLoader.laodJSONDefinition(jsonFile);
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("layout",layoutProcess(jsonData));
        paramMap.put("element",element(jsonData));
        Template template = configuration.getTemplate("home.ftl");
        return executeDef(jsonData,template,paramMap);
    }

    private Map<String,Object> layoutProcess(String  jsonData) throws IOException, TemplateException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject definitions = jsonObject.getJSONObject("definitions");
        String layout = definitions.getString("layout");
        File file = Paths.get("json-schema", layout).toFile();
        return JSONLoader.mapper(JSONLoader.laodJSONDefinition(file));
    }

    private Map<String,Object> element(String  jsonData) throws IOException, TemplateException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject definitions = jsonObject.getJSONObject("definitions");
        JSONArray elements = definitions.getJSONArray("elements");
        Map<String, Object> def=new HashMap<>();
        for (int i = 0; i < elements.length(); i++) {
            JSONObject elementsJSONObject = elements.getJSONObject(i);
            String $ref = elementsJSONObject.getString("$ref");
            File file = Paths.get("json-schema", $ref).toFile();
            Map<String, Object> definitionsMap = (Map<String, Object>) JSONLoader.mapper(JSONLoader.laodJSONDefinition(file));
            def.putAll(definitionsMap);
        }
        return def;
    }

    public String execute(File jsonFile) throws IOException, TemplateException {
        String jsonData = JSONLoader.laodJSONDefinition(jsonFile);
        return execute(jsonData);
    }

    public String execute(String json) throws IOException, TemplateException {
        Template template = configuration.getTemplate("body.ftl");
        return execute(json, template);
    }

    private String execute(String json, Template template) throws IOException, TemplateException {
        return execute(json, template, Collections.EMPTY_MAP);
    }

    private String execute(String json, Template template, Map<String, Object> paramMap) throws IOException, TemplateException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("element",JSONLoader.mapper(json));
        dataMap.putAll(paramMap);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
    }

    private String executeDef(String json, Template template, Map<String, Object> paramMap) throws IOException, TemplateException {
        Map<String, Object> dataMap = JSONLoader.mapper(json);
        dataMap.putAll(paramMap);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
    }

}
