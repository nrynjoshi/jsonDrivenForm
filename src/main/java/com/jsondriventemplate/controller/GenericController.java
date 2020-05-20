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

import java.util.Map;

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
    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.SAVE)
    public void saveRecord(@RequestBody MultiValueMap map) throws Exception {
        Map map1 = map.toSingleValueMap();
        map1.put("type","create");
        AppInject.jdtScript.process(map1);
    }

    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.UPDATE)
    public void updateRecord(@RequestBody MultiValueMap map) throws Exception {
        Map map1 = map.toSingleValueMap();
        map1.put("type","update");
        AppInject.jdtScript.process(map1);
    }

    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.SEARCH)
    public void searchRecord(@RequestBody MultiValueMap map) throws Exception {
        Map map1 = map.toSingleValueMap();
        map1.put("type","search");
        AppInject.jdtScript.process(map1);
    }

    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.DELETE)
    public void deleteRecord(@RequestBody MultiValueMap map) throws Exception {
        Map map1 = map.toSingleValueMap();
        map1.put("type","delete");
        AppInject.jdtScript.process(map1);
    }
    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.GET_ID)
    public void getById(@RequestBody MultiValueMap map) throws Exception {
        Map map1 = map.toSingleValueMap();
        map1.put("type","retrieveByID");
        AppInject.jdtScript.process(map1);
    }
    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.GET)
    public void getAll(@RequestBody MultiValueMap map) throws Exception {
        Map map1 = map.toSingleValueMap();
        map1.put("type","retrieve");
        AppInject.jdtScript.process(map1);
    }



}
