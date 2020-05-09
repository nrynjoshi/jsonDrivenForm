package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
@RequestMapping("auth")
public class GenericController {

    @GetMapping("/{uri}")
    public String loginPage(Model model, @PathVariable String uri) throws Exception {
        AppInject.templateParser.validateURIJSON(uri);
        File file = Paths.get("json-schema", uri+".json").toFile();
        model.addAttribute("template",AppInject.templateParser.pageDefinition(file));
        return "auth/index";
    }

    @GetMapping("/process/{uri}")
    public String loginPage(Model model) throws IOException, TemplateException {
        File file = Paths.get("json-schema", "sample.json").toFile();
        String execute = AppInject.templateParser.execute(file);
        model.addAttribute("JSONHtmlTemplate", execute);
        return "auth/index";
    }

}
