package com.jsondriventemplate;

import com.jsondriventemplate.config.MessageReader;
import freemarker.template.Configuration;
import org.springframework.stereotype.Component;

@Component
public class AppInject {

    public static TemplateParser templateParser;
    public static Configuration configuration;
    public static MessageReader messageReader;

    public AppInject(TemplateParser templateParser, MessageReader messageReader,
                     Configuration configuration) {
        this.templateParser = templateParser;
        this.messageReader = messageReader;
        this.configuration = configuration;
    }
}
