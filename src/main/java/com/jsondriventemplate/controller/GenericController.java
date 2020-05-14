package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import com.jsondriventemplate.repo.DBConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(value = Endpoints.AUTH)
public class GenericController {

    @GetMapping(value = Endpoints.URI)
    public String loginPage(Model model, @PathVariable String uri) throws Exception {
        String jsonData = AppInject.templateService.getJSONFromURI(uri);
        model.addAttribute(JSONTemplateConst.TEMPLATE,AppInject.templateParser.pageDefinition(jsonData));
        return ViewResolver.AUTH_INDEX;
    }

}
