package com.jsondriventemplate.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class MessageReader {

    private final MessageSource messageSource;

    private MessageSourceAccessor accessor;
    
    public MessageReader(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
    }

    public String get(String key){
        String message = accessor.getMessage(key);
        if(StringUtils.isBlank(message)){
            return  messageSource.getMessage("error.employee.contact", null, Locale.getDefault());
        }
        return message;
    }

}
