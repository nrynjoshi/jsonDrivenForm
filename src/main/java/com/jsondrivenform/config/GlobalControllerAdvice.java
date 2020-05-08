package com.jsondrivenform.config;

import com.jsondrivenform.exception.URINotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(URINotFoundException.class)
    public String notFound(Model model, Exception x){
        x.printStackTrace();
        model.addAttribute("errorMessage",x.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String global(Model model, Exception x){
        x.printStackTrace();
        model.addAttribute("errorMessage",x.getMessage());
        return "error";
    }

}
