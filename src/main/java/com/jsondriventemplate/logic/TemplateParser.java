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
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
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
        return pageDefinition(uri, jsonData, false, type, null);
    }

    public String pageDefinitionInnerBody(String uri, String jsonData, String type, String id, boolean isInnerBodyOnly) throws IOException, TemplateException {
        return pageDefinition(uri, jsonData, true, isInnerBodyOnly, type, id, null);
    }

    public String pageDefinition(String uri, String jsonData, boolean isAdminPreview, String type, String id) throws IOException, TemplateException {
        return pageDefinition(uri, jsonData, isAdminPreview, false, type, id, null);
    }

    public String pageDefinition(String uri, String jsonData, boolean isAdminPreview, boolean isInnerBodyOnly, String type, String id, List<Map> value) throws IOException, TemplateException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uri", uri);
        paramMap.put("type", type);
        jsonData = cleanUpJSONData(jsonData, paramMap);
        try {
            paramMap.put(JSONTemplateConst.LAYOUT, layoutProcess(jsonData));
            paramMap.put(JSONTemplateConst.ELEMENTS, element(jsonData, type));
            if (StringUtils.isNotBlank(id)) {
                paramMap.put("_id", id);
            }
            if (value != null) {
                paramMap.put("list_value", value);
            } else {
                if (StringUtils.isBlank(type)) {
                    type = "list";
                }
                if (StringUtils.isNotBlank(type)) {
                    if ("list".equals(type)) {
                        List<?> all = AppInject.mongoClientProvider.findAll(uri);
                        paramMap.put("list_value", all);
                    }
                }

            }


        } catch (Exception x) {
            if (!isAdminPreview) {
                throw x;
            }
        }
        String templateFtl = "home.ftl";
        if (isInnerBodyOnly) {
            templateFtl = "innerbody.ftl";
        }
        Template template = AppInject.configuration.getTemplate(templateFtl);
        return executeDef(jsonData, template, paramMap);
    }

    private Map<String, Object> layoutProcess(String jsonData) throws IOException {
        String layout = JsonPath.parse(jsonData).read("$['definitions']['page']['layout']");
//        File file = Paths.get(JSONTemplateConst.JSON_SCHEMA_ATTR, layout).toFile();
        File file = ResourceUtils.getFile("classpath:" + JSONTemplateConst.JSON_SCHEMA_ATTR + layout);
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

    private String executeDef(String json, Template template, Map<String, Object> paramMap) throws IOException, TemplateException {
        Map<String, Object> dataMap = JSONLoader.mapper(json);
        dataMap.putAll(paramMap);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
    }

    private String cleanUpJSONData(String jsonData, Map<String, Object> param) {
        org.apache.commons.text.StrSubstitutor substitutor = new org.apache.commons.text.StrSubstitutor(param);
        return substitutor.replace(jsonData);
    }

}
