package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import com.jsondriventemplate.exception.JSONValidationException;
import com.jsondriventemplate.repo.DBConstant;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping(value = Endpoints.ADMIN)
public class AdminController {

    @GetMapping(value = Endpoints.EDITOR)
    public String editor(Model model, @RequestParam(name = JSONTemplateConst.query, defaultValue = "", required = false) String query) throws Exception {
        Map jsonData=null;
        String previewURL="";
        if(StringUtils.isNotBlank(query)){
            jsonData = AppInject.templateService.getJSONFromURIEditorView(query);
            previewURL="/admin/preview/"+query;
        }

        if(jsonData==null){
            jsonData=new HashMap();
            jsonData.put("_id",AppInject.templateService.getURIID(query));
            jsonData.put("json","{}");
        }

        model.addAttribute("query",query);
        model.addAttribute(JSONTemplateConst.PREVIEW_URL, previewURL);
        model.addAttribute(JSONTemplateConst.unProcessedJSON, jsonData);
        model.addAttribute(JSONTemplateConst.jsonList, AppInject.mongoClientProvider.findAll(DBConstant.TEMPLATE_INFORMATION));
        return ViewResolver.ADMIN_EDITOR;
    }

    @PostMapping(value = Endpoints.EDITOR)
    public @ResponseBody
    String pageUpdate(@RequestBody MultiValueMap jsonMap) throws IOException, TemplateException {
        String jsonData = (String) jsonMap.toSingleValueMap().get("json");
        if (StringUtils.isBlank(jsonData)) {
            return "Non thing to display..";
        }
        try{
           return AppInject.templateParser.pageDefinition(null,jsonData,"");
        }catch (Exception x){
            return "";
        }

    }

    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = Endpoints.JSON_TEMPLATE)
    public String jsonTemplate(Model model) {
        List all = AppInject.mongoClientProvider.findAll(DBConstant.TEMPLATE_INFORMATION);
        model.addAttribute("templateList", all);
        return ViewResolver.ADMIN_JSON_TEMPLATE;
    }

    @PostMapping(value = Endpoints.JSON_TEMPLATE)
    public String saveJsonTemplateInfo(@RequestBody MultiValueMap valueMap) {
        Map dataMap = valueMap.toSingleValueMap();
        Map data  = AppInject.mongoClientProvider.findByAtt("url",(String)dataMap.get("url"),DBConstant.TEMPLATE_INFORMATION);
        if(data==null) {
            AppInject.mongoClientProvider.save(dataMap, DBConstant.TEMPLATE_INFORMATION);
        }else if(dataMap.containsKey("_id") && dataMap.get("_id").toString().equals(data.get("_id").toString())) {
            AppInject.mongoClientProvider.save(dataMap, DBConstant.TEMPLATE_INFORMATION);
        }
        return "redirect:" + Endpoints.ADMIN + Endpoints.JSON_TEMPLATE;
    }

    @GetMapping(value = Endpoints.JSON_TEMPLATE + Endpoints.ID)
    public String deleteJsonTemplateInfo(@PathVariable String id) {
        AppInject.mongoClientProvider.delete(id, DBConstant.TEMPLATE_INFORMATION);
        AppInject.mongoClientProvider.delete(id, DBConstant.JSON_TEMPLATE_DEFINITION);
        return "redirect:" + Endpoints.ADMIN + Endpoints.JSON_TEMPLATE;
    }

    @GetMapping(value = Endpoints.JSON_TEMPLATE +Endpoints.EDIT+ Endpoints.ID)
    public String editJsonTemplateInfo(@PathVariable String id,Model model) {
        Map data = AppInject.mongoClientProvider.findById(id,DBConstant.TEMPLATE_INFORMATION);
        model.addAttribute("template", data);
        return ViewResolver.ADMIN_JSON_TEMPLATE_EDIT;
    }

    @PostMapping(value = Endpoints.JSON_DEFINITION)
    public @ResponseBody String saveJsonDefintion(@RequestBody MultiValueMap<String, Object> map) throws JSONValidationException {
        Map<String, Object> stringObjectMap = map.toSingleValueMap();
        String json = AppInject.jsonValidator.validJSONFormat((String) stringObjectMap.get("json"));
        stringObjectMap.put("json",json);
        AppInject.mongoClientProvider.save(stringObjectMap, DBConstant.JSON_TEMPLATE_DEFINITION);
        return "success";
    }


}
