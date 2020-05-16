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
public class GenericController {

    @GetMapping(value = Endpoints.AUTH+Endpoints.URI)
    public String globalPage(Model model, @PathVariable String uri) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        model.addAttribute(JSONTemplateConst.TEMPLATE,AppInject.templateParser.pageDefinition(jsonData));
        return ViewResolver.AUTH_INDEX;
    }

    @GetMapping(value = Endpoints.PREVIEW+Endpoints.URI)
    public String previewPage(Model model, @PathVariable String uri) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        model.addAttribute(JSONTemplateConst.TEMPLATE,AppInject.templateParser.pageDefinition(jsonData,true));
        return ViewResolver.AUTH_INDEX;
    }


}
