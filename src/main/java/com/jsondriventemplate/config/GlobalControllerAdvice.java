package com.jsondriventemplate.config;

import com.jsondriventemplate.exception.URINotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(URINotFoundException.class)
    public String notFound(Model model, Exception x){
        x.printStackTrace();
        model.addAttribute("errorMessage",x.getMessage());
        return "error/404";
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public String accessDeniedException(Model model, Exception x){
        x.printStackTrace();
        model.addAttribute("errorMessage",x.getMessage());
        return "error/403";
    }

    @ExceptionHandler(Exception.class)
    public String global(Model model, Exception x){
        x.printStackTrace();
        model.addAttribute("errorMessage",x.getMessage());
        return "error/-1";
    }

}
