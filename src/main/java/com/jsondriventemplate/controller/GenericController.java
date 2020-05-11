package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.nio.file.Paths;

@Controller
@RequestMapping(value = Endpoints.AUTH)
public class GenericController {

    @GetMapping(value = Endpoints.URI)
    public String loginPage(Model model, @PathVariable String uri) throws Exception {
        AppInject.templateParser.validateURIJSON(uri);
        File file = Paths.get(JSONTemplateConst.JSON_SCHEMA_ATTR, uri+".json").toFile();
        model.addAttribute(JSONTemplateConst.TEMPLATE,AppInject.templateParser.pageDefinition(file));
        return ViewResolver.AUTH_INDEX;
    }

}
