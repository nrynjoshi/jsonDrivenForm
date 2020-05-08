package com.jsondrivenform.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class MessageReader {

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;
    private static final String defaultMessage="Something went wrong. Please contact administrator for futher information.";

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
    }

    public String get(String key){
        String message = accessor.getMessage(key);
        if(StringUtils.isBlank(message)){
            return defaultMessage;
        }
        return message;
    }

}
