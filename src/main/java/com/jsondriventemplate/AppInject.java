package com.jsondriventemplate;

import com.jsondriventemplate.config.MessageReader;
import com.jsondriventemplate.logic.JsonTemplateService;
import com.jsondriventemplate.logic.TemplateParser;
import com.jsondriventemplate.repo.MongoClientProvider;
import freemarker.template.Configuration;
import org.springframework.stereotype.Component;

@Component
public class AppInject {

    public static TemplateParser templateParser;
    public static Configuration configuration;
    public static MessageReader messageReader;
    public static MongoClientProvider mongoClientProvider;
    public static JsonTemplateService templateService;

    public AppInject(TemplateParser templateParser, MessageReader messageReader, MongoClientProvider mongoClientProvider, JsonTemplateService templateService,
                     Configuration configuration) {
        AppInject.templateParser = templateParser;
        AppInject.messageReader = messageReader;
        AppInject.configuration = configuration;
        AppInject.mongoClientProvider = mongoClientProvider;
        AppInject.templateService = templateService;
    }
}
