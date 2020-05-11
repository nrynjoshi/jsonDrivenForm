package com.jsondriventemplate;

import com.jsondriventemplate.config.MessageReader;
import com.jsondriventemplate.logic.TemplateParser;
import freemarker.template.Configuration;
import org.springframework.stereotype.Component;

@Component
public class AppInject {

    public static TemplateParser templateParser;
    public static Configuration configuration;
    public static MessageReader messageReader;

    public AppInject(TemplateParser templateParser, MessageReader messageReader,
                     Configuration configuration) {
        AppInject.templateParser = templateParser;
        AppInject.messageReader = messageReader;
        AppInject.configuration = configuration;
    }
}
