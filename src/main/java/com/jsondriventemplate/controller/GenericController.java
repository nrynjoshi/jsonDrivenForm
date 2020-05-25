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

    //------------------- Post and get Function will be used for all json request as per script -------------------------

    @PostMapping(value = Endpoints.AUTH + Endpoints.PROCESS+Endpoints.UPDATE)
    public String updateRecord(@RequestBody MultiValueMap map) throws Exception {
        Map map1 = map.toSingleValueMap();
        String uri = (String) map1.get("uri");
        map1.put("type","create");
        AppInject.jdtScript.process(map1);
        return "redirect:/auth/"+uri+"?type=list";
    }

}
