package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GenericController {

    @GetMapping(value = Endpoints.AUTH + Endpoints.URI)
    public String globalPage(Model model, @PathVariable String uri,@RequestParam(required = false) String type,@RequestParam(required = false) String id) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        model.addAttribute("uri",uri);
        model.addAttribute("type",type);
        model.addAttribute(JSONTemplateConst.TEMPLATE, AppInject.templateParser.pageDefinition(uri,jsonData,type,id));
        return ViewResolver.AUTH_INDEX;
    }

    @GetMapping(value = Endpoints.PREVIEW + Endpoints.URI)
    public String previewPage(Model model, @PathVariable String uri,@RequestParam(required = false) String type,@RequestParam(required = false) String id) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        model.addAttribute("uri",uri);
        model.addAttribute("type",type);
        model.addAttribute(JSONTemplateConst.TEMPLATE, AppInject.templateParser.pageDefinition(uri,jsonData, true,type,id));
        return ViewResolver.AUTH_INDEX;
    }
    //------------------- Post and get Function will be used for all json request as per script -------------------------

    // TODO: 5/18/2020 on development
    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.SAVE)
    public String saveRecord(@RequestBody MultiValueMap map) throws Exception {
        Map map1 = map.toSingleValueMap();
        String uri = (String) map1.get("uri");
        map1.put("type","create");
        AppInject.jdtScript.process(map1);
        return "redirect:/auth/"+uri+"?type=list";
    }

    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.SEARCH)
    public @ResponseBody String searchRecord(@RequestBody MultiValueMap map) throws Exception {
        Map map1 = map.toSingleValueMap();
        map1.put("type","search");
        String uri = (String) map1.get("uri");
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        return  AppInject.templateParser.pageDefinition(uri,jsonData,false,true,"list",null,(List<Map>) AppInject.jdtScript.processAndReturn(map1));
    }

    @GetMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.DELETE)
    public String deleteRecord(@RequestParam String id,@RequestParam String uri) throws Exception {
        Map map1 = new HashMap();
        map1.put("type","delete");
        map1.put("_id",id);
        map1.put("uri",uri);
        AppInject.jdtScript.process(map1);
        return "redirect:/auth/"+uri+"?type=list";
    }

    @GetMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.GET_ID)
    public @ResponseBody Map getById(@RequestParam String id,@RequestParam String uri) throws Exception {
        Map map1 = new HashMap();
        map1.put("type","retrieveByID");
        map1.put("_id",id);
        map1.put("uri",uri);
        return (Map) AppInject.jdtScript.processAndReturn(map1);
    }
    @GetMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.GET)
    public void getAll(@RequestParam String uri) throws Exception {
        Map map1 = new HashMap();
        map1.put("type","retrieve");
        map1.put("uri",uri);
        AppInject.jdtScript.process(map1);
    }

}
