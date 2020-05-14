package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import com.jsondriventemplate.constant.AppConstant;
import com.jsondriventemplate.constant.UrlConstant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.nio.file.Paths;

@Controller
public class HomeController {

    @GetMapping(value = UrlConstant.CONTEXT)
    public String indexPage() {
        return ViewResolver.INDEX;
    }

    @GetMapping(value = UrlConstant.LOGIN)
    public String loginPage(Model model) throws Exception {
        String jsonData = AppInject.templateService.getJSONFromURI(AppConstant.LOGIN);
        model.addAttribute(JSONTemplateConst.TEMPLATE, AppInject.templateParser.pageDefinition(jsonData));
        return ViewResolver.AUTH_INDEX;
    }
}
