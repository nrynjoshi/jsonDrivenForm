package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = Endpoints.ADMIN)
public class AdminGenericController {


    @GetMapping(value = Endpoints.URI)
    public String globalPage(Model model, @PathVariable String uri, @RequestParam(required = false) String type,@RequestParam(required = false) String id) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        model.addAttribute("uri",uri);
        model.addAttribute("type",type);
        model.addAttribute("_id",id);
        model.addAttribute(JSONTemplateConst.TEMPLATE, AppInject.templateParser.pageDefinitionInnerBody(uri,jsonData,type,id,true));
        return ViewResolver.ADMIN_INDEX;
    }


    @GetMapping(value = Endpoints.PREVIEW + Endpoints.URI)
    public String previewPage(Model model, @PathVariable String uri,@RequestParam(required = false) String type,@RequestParam(required = false) String id) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        model.addAttribute("uri",uri);
        model.addAttribute("type",type);
        model.addAttribute(JSONTemplateConst.TEMPLATE, AppInject.templateParser.pageDefinition(uri,jsonData, true,type,id));
        return ViewResolver.AUTH_INDEX;
    }

    //--------------------------------------------------------------------------------------------
    @PostMapping(value = Endpoints.PROCESS+Endpoints.SAVE)
    public String saveRecord(@RequestBody MultiValueMap map) throws Exception {
        Map<String,Object> map1 = map.toSingleValueMap();
        String uri = (String) map1.get("uri");
        map1.put("type","create");
        AppInject.jdtScript.process(map1);
        return "redirect:/admin/"+uri+"?type=list";
    }

    @PostMapping(value = Endpoints.PROCESS+Endpoints.UPDATE)
    public String updateRecord(@RequestBody MultiValueMap map) throws Exception {
        Map<String,Object> map1 = map.toSingleValueMap();
        String uri = (String) map1.get("uri");
        map1.put("type","update");
        AppInject.jdtScript.process(map1);
        return "redirect:/admin/"+uri+"?type=list";
    }

    @PostMapping(value = Endpoints.PROCESS+Endpoints.SEARCH)
    public @ResponseBody String searchRecord(@RequestBody MultiValueMap map) throws Exception {
        Map<String,Object> map1 = map.toSingleValueMap();
        map1.put("type","search");
        String uri = (String) map1.get("uri");
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        return  AppInject.templateParser.pageDefinition(uri,jsonData,false,true,"list",null,(List<Map>) AppInject.jdtScript.processAndReturn(map1));
    }

    @GetMapping(value = Endpoints.PROCESS+Endpoints.DELETE)
    public String deleteRecord(@RequestParam String id,@RequestParam String uri) throws Exception {
        Map<String,Object> map1 = new HashMap();
        map1.put("type","delete");
        map1.put("_id",id);
        map1.put("uri",uri);
        AppInject.jdtScript.process(map1);
        return "redirect:/admin/"+uri+"?type=list";
    }

    @GetMapping(value = Endpoints.PROCESS+Endpoints.GET_ID)
    public @ResponseBody Map getById(@RequestParam String id,@RequestParam String uri) throws Exception {
        Map<String,Object> map1 = new HashMap();
        map1.put("type","retrieveByID");
        map1.put("_id",id);
        map1.put("uri",uri);
        return (Map) AppInject.jdtScript.processAndReturn(map1);
    }
    @GetMapping(value = Endpoints.PROCESS+Endpoints.GET)
    public void getAll(@RequestParam String uri) throws Exception {
        Map<String,Object> map1 = new HashMap();
        map1.put("type","retrieve");
        map1.put("uri",uri);
        AppInject.jdtScript.process(map1);
    }

}
