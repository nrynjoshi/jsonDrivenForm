package com.jsondriventemplate.config;

import com.jsondriventemplate.exception.AuthenticationException;
import com.jsondriventemplate.exception.URINotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(URINotFoundException.class)
    public String notFound(Model model, Exception x){
        process(model, x);
        return "error/404";
    }

    private void process(Model model, Exception x) {
        x.printStackTrace();
        model.addAttribute("errorMessage", x.getMessage());
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public String accessDeniedException(Model model, Exception x){
        process(model, x);
        return "error/403";
    }

    @ExceptionHandler(AuthenticationException.class)
    public String authenticationException(Model model, Exception x){
        process(model, x);
        return "/login";
    }

    @ExceptionHandler(Exception.class)
    public String global(Model model, Exception x){
        process(model, x);
        return "error/-1";
    }

}
