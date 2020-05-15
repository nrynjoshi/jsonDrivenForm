package com.jsondriventemplate.config;

import com.jsondriventemplate.exception.AuthenticationException;
import com.jsondriventemplate.exception.JSONValidationException;
import com.jsondriventemplate.exception.URINotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(URINotFoundException.class)
    public String notFound(Model model, Exception x) {
        process(model, x);
        return "error/404";
    }



    @ExceptionHandler(freemarker.core.NonSequenceOrCollectionException.class)
    public String parserError(Model model, Exception x) {
        // TODO: 5/11/2020 to notify administrator if something went wrong while converting JSON to view
        process(model, x);
        return "error/-1";
    }

    private void process(Model model, Exception x) {
        x.printStackTrace();
        model.addAttribute("errorMessage", x.getMessage());
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public String accessDeniedException(Model model, Exception x) {
        process(model, x);
        return "error/403";
    }

    @ExceptionHandler(AuthenticationException.class)
    public String authenticationException(Model model, Exception x) {
        process(model, x);
        return "/login";
    }

    @ExceptionHandler(JSONValidationException.class)
    public @ResponseBody
    ResponseEntity jsonValidationException(JSONValidationException x) {
        x.printStackTrace();
        return new ResponseEntity<String>(x.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public String global(Model model, Exception x) {
        process(model, x);
        return "error/-1";
    }

}
