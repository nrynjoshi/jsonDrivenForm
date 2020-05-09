package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.nio.file.Paths;

@Controller
public class HomeController {

    @GetMapping("/")
    public String indexPage(Model model) throws Exception {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model) throws Exception {
        String login = "login";
        AppInject.templateParser.validateURIJSON(login);
        File file = Paths.get("json-schema", login + ".json").toFile();
        model.addAttribute("template", AppInject.templateParser.pageDefinition(file));
        return "auth/index";
    }
}
