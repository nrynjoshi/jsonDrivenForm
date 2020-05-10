package com.jsondriventemplate;

import com.jayway.jsonpath.JsonPath;
import com.jsondriventemplate.exception.URINotFoundException;
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
        File file = Paths.get("json-schema", uri + ".json").toFile();
        if (!file.exists()) {
            throw new URINotFoundException(AppInject.messageReader.get(StatusCode.NOT_FOUND.value()));
        }
    }

    public String pageDefinition(File jsonFile) throws IOException, TemplateException {
        String jsonData = JSONLoader.laodJSONDefinition(jsonFile);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("layout", layoutProcess(jsonData));
        paramMap.put("elements", element(jsonData));
        Template template = AppInject.configuration.getTemplate("home.ftl");
        return executeDef(jsonData, template, paramMap);
    }


    private Map<String, Object> layoutProcess(String jsonData) throws IOException {
        String layout = JsonPath.parse(jsonData).read("$['definitions']['page']['layout']");
        File file = Paths.get("json-schema", layout).toFile();
        return JSONLoader.mapper(JSONLoader.laodJSONDefinition(file));
    }

    private List<LinkedHashMap> element(String jsonData) {
        net.minidev.json.JSONArray elements = JsonPath.parse(jsonData).read("$['definitions']['page']['elements']");
        return elementFilter(jsonData, elements);
    }

    private List<LinkedHashMap> elementFilter(String jsonData, JSONArray elements) {
        StringBuilder builder = new StringBuilder("$");
        List<LinkedHashMap> elementList = new ArrayList<>();
        for (Object element : elements) {
            LinkedHashMap elementsJSONObject = (LinkedHashMap) element;
            String $ref = (String) elementsJSONObject.get("$ref");
            String[] splitVal = StringUtils.split($ref, "/");
            for (String val : splitVal) {
                if (StringUtils.equalsAnyIgnoreCase(val, "#")) {
                    continue;
                }
                builder.append("['").append(val).append("']");
            }
            LinkedHashMap jsonElement = JsonPath.parse(jsonData).read(builder.toString().trim());
            elementList.add(jsonElement);
        }
        return elementList;
    }

    public String execute(File jsonFile) throws IOException, TemplateException {
        String jsonData = JSONLoader.laodJSONDefinition(jsonFile);
        return execute(jsonData);
    }

    public String execute(String json) throws IOException, TemplateException {
        Template template = AppInject.configuration.getTemplate("body.ftl");
        return execute(json, template);
    }

    private String execute(String json, Template template) throws IOException, TemplateException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("element", JSONLoader.mapper(json));
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
    }

    private String executeDef(String json, Template template, Map<String, Object> paramMap) throws IOException, TemplateException {
        Map<String, Object> dataMap = JSONLoader.mapper(json);
        dataMap.putAll(paramMap);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
    }

}
