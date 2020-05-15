package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import com.jsondriventemplate.repo.DBConstant;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping(value = Endpoints.ADMIN)
public class TemplateManipulateController {

    @GetMapping(value = Endpoints.DASHBOARD)
    public String dashboard() {
        return ViewResolver.ADMIN_DASHBOARD;
    }

    @GetMapping(value = Endpoints.EDITOR)
    public String editor(Model model, @RequestParam(name = JSONTemplateConst.query, defaultValue = "", required = false) String query) throws Exception {
        Map jsonData=null;
        String previewURL="";
        if(StringUtils.isNotBlank(query)){
            jsonData = AppInject.templateService.getJSONFromURIEditorView(query);
            previewURL="/auth/"+query;
        }

        if(jsonData==null){
            jsonData=new HashMap();
            jsonData.put("_id","");
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
           return AppInject.templateParser.pageDefinition(jsonData);
        }catch (Exception x){
            return AppInject.templateParser.execute(jsonData);
        }

    }


}
