package com.jsondrivenform;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class TemplateParser {

    @Autowired
    private Configuration configuration;


    public String execute(File jsonFile) throws IOException, TemplateException {
        String jsonData = JSONLoadUtil.laodFormDefinition(jsonFile);
        return execute(jsonData);
    }

    public String execute(String json) throws IOException, TemplateException {
        Template template = configuration.getTemplate("home.ftl");
        Map<String, Object> dataMap = JSONLoadUtil.mapper(json);
        String processedTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
        return processedTemplate;
    }

}
