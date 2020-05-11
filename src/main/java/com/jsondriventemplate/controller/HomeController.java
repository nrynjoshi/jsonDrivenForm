package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.nio.file.Paths;

@Controller
public class HomeController {

    @GetMapping(value = Endpoints.CONTEXT)
    public String indexPage() {
        return ViewResolver.INDEX;
    }

    @GetMapping(value = Endpoints.LOGIN)
    public String loginPage(Model model) throws Exception {
        String login = "login";
        AppInject.templateParser.validateURIJSON(login);
        File file = Paths.get(JSONTemplateConst.JSON_SCHEMA_ATTR, login + ".json").toFile();
        model.addAttribute(JSONTemplateConst.TEMPLATE, AppInject.templateParser.pageDefinition(file));
        return ViewResolver.AUTH_INDEX;
    }
}
