package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GenericController {

    @GetMapping(value = Endpoints.AUTH + Endpoints.URI)
    public String globalPage(Model model, @PathVariable String uri) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        model.addAttribute("uri",uri);
        model.addAttribute(JSONTemplateConst.TEMPLATE, AppInject.templateParser.pageDefinition(jsonData));
        return ViewResolver.AUTH_INDEX;
    }

    @GetMapping(value = Endpoints.PREVIEW + Endpoints.URI)
    public String previewPage(Model model, @PathVariable String uri) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        model.addAttribute(JSONTemplateConst.TEMPLATE, AppInject.templateParser.pageDefinition(jsonData, true));
        return ViewResolver.AUTH_INDEX;
    }
    //------------------- Post and get Function will be used for all json request as per script -------------------------

    // TODO: 5/18/2020 on development
    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS)
    public void post(@RequestBody MultiValueMap map) throws Exception {
        AppInject.jdtScript.process(map.toSingleValueMap());
    }

    // TODO: 5/18/2020 on development
    @GetMapping(value = Endpoints.AUTH + Endpoints.PROCESS)
    public String get(@RequestBody MultiValueMap requestDTO) throws Exception {
        return (String) AppInject.jdtScript.processAndReturn(requestDTO.toSingleValueMap());
    }


}
