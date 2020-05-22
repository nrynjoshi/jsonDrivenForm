package com.jsondriventemplate.logic;

import com.jayway.jsonpath.JsonPath;
import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import com.jsondriventemplate.StatusCode;
import com.jsondriventemplate.exception.URINotFoundException;
import com.jsondriventemplate.repo.DBConstant;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Component
public class TemplateParser {


    public void validateURIJSON(String uri) throws Exception {
        if (StringUtils.isBlank(uri)) {
            throw new URINotFoundException(AppInject.messageReader.get(StatusCode.NOT_FOUND.value()));
        }
        Map byURL = AppInject.mongoClientProvider.findByURL(uri, DBConstant.TEMPLATE_INFORMATION);
        if (byURL == null || byURL.isEmpty()) {
            throw new URINotFoundException(AppInject.messageReader.get(StatusCode.NOT_FOUND.value()));
        }
    }

    public String pageDefinition(String uri, String jsonData, String type) throws IOException, TemplateException {
        return pageDefinition(uri, jsonData, false, type);
    }

    public String pageDefinition(String uri, String jsonData, boolean isAdminPreview, String type) throws IOException, TemplateException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uri", uri);
        jsonData = cleanUpJSONData(jsonData, paramMap);
        try {
            paramMap.put(JSONTemplateConst.LAYOUT, layoutProcess(jsonData));
            paramMap.put(JSONTemplateConst.ELEMENTS, element(jsonData, type));

            try{
                if (StringUtils.isBlank(type)) {
                    type="list";
                }
                if (StringUtils.isNotBlank(type)) {
                    switch (type) {
                        case "list":
                            List<?> all = AppInject.mongoClientProvider.findAll(uri);
                            paramMap.put("list_value", all);
                            break;
                    }
                }
            }catch (Exception x){
                type=null;
            }

        } catch (Exception x) {
            if (!isAdminPreview) {
                throw x;
            }
        }
        Template template = AppInject.configuration.getTemplate("home.ftl");
        return executeDef(jsonData, template, paramMap);
    }

    private Map<String, Object> layoutProcess(String jsonData) throws IOException {
        String layout = JsonPath.parse(jsonData).read("$['definitions']['page']['layout']");
        File file = Paths.get(JSONTemplateConst.JSON_SCHEMA_ATTR, layout).toFile();
        return JSONLoader.mapper(JSONLoader.laodJSONDefinition(file));
    }

    private List<LinkedHashMap> element(String jsonData, String type) {

        net.minidev.json.JSONArray elements = JsonPath.parse(jsonData).read("$['definitions']['page']['elements']");
        return elementFilter(jsonData, elements, type);
    }

    private List<LinkedHashMap> elementFilter(String jsonData, JSONArray elements, String type) {

        List<LinkedHashMap> elementList = new ArrayList<>();
        for (Object element : elements) {
            StringBuilder builder = new StringBuilder("$");
            LinkedHashMap elementsJSONObject = (LinkedHashMap) element;
            String $ref = (String) elementsJSONObject.get("$ref");
            String[] splitVal = StringUtils.split($ref, "/");
            String lastType = null;
            for (String val : splitVal) {
                if (StringUtils.equalsAnyIgnoreCase(val, "#")) {
                    continue;
                }
                builder.append("['").append(val).append("']");
                lastType = val;
            }
            if (StringUtils.isNotBlank(type)) {
                if (StringUtils.equalsAnyIgnoreCase(lastType, type)) {
                    LinkedHashMap jsonElement = JsonPath.parse(jsonData).read(builder.toString().trim());
                    elementList.add(jsonElement);
                    break;
                }
                continue;
            }
            LinkedHashMap jsonElement = JsonPath.parse(jsonData).read(builder.toString().trim());
            elementList.add(jsonElement);
        }
        return elementList;
    }

    public String execute(String json) throws IOException, TemplateException {
        Template template = AppInject.configuration.getTemplate("body.ftl");
        return execute(json, template);
    }

    private String execute(String json, Template template) throws IOException, TemplateException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(JSONTemplateConst.ELEMENTS, JSONLoader.mapper(json));
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
    }

    private String executeDef(String json, Template template, Map<String, Object> paramMap) throws IOException, TemplateException {
        Map<String, Object> dataMap = JSONLoader.mapper(json);
        dataMap.putAll(paramMap);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
    }

    private String cleanUpJSONData(String jsonData, Map param) {
        org.apache.commons.text.StrSubstitutor substitutor = new org.apache.commons.text.StrSubstitutor(param);
        return substitutor.replace(jsonData);
    }

}
