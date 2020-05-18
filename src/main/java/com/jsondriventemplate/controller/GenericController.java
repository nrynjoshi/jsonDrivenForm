package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import com.jsondriventemplate.dto.RequestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GenericController {

    @GetMapping(value = Endpoints.AUTH + Endpoints.URI)
    public String globalPage(Model model, @PathVariable String uri) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
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
    @PostMapping(value = Endpoints.PREVIEW + Endpoints.PROCESS)
    public void post(@RequestBody RequestDTO requestDTO) throws Exception {
        AppInject.jdtScript.process(requestDTO);
    }

    // TODO: 5/18/2020 on development
    @PostMapping(value = Endpoints.PREVIEW + Endpoints.PROCESS)
    public String get(@RequestBody RequestDTO requestDTO) throws Exception {
        return AppInject.jdtScript.processAndReturn(requestDTO);
    }


}
