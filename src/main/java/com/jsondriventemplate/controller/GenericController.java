package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GenericController {

    @GetMapping(value = Endpoints.AUTH + Endpoints.URI)
    public String globalPage(Model model, @PathVariable String uri,@RequestParam(required = false) String type,@RequestParam(required = false) String id) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        model.addAttribute("uri","employee");
        model.addAttribute("type",type);
        model.addAttribute("_id",id);
        model.addAttribute(JSONTemplateConst.TEMPLATE, AppInject.templateParser.pageDefinitionInnerBody(uri,jsonData,type,id,true));
        return ViewResolver.AUTH_INDEX;
    }

    //------------------- Post and get Function will be used for all json request as per script -------------------------
    @GetMapping(value =Endpoints.AUTH+Endpoints.PROCESS+Endpoints.GET_ID)
    public @ResponseBody Map getById(@RequestParam String id,@RequestParam String uri) throws Exception {
        Map<String,Object> map1 = new HashMap();
        map1.put("type","retrieveByID");
        map1.put("_id",id);
        map1.put("uri",uri);
        return (Map) AppInject.jdtScript.processAndReturn(map1);
    }
    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.UPDATE)
    public String updateRecord(@RequestBody MultiValueMap map) throws Exception {
        Map<String,Object> map1 = map.toSingleValueMap();
        map1.put("type","create");
        AppInject.jdtScript.process(map1);
        return "redirect:"+Endpoints.AUTH+Endpoints.AUTH_DASHBOARD+"?type=update&id="+map1.get("_id");
    }

}
