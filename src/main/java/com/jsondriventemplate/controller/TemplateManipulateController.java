package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = Endpoints.ADMIN)
public class TemplateManipulateController {

    @GetMapping(value = Endpoints.DASHBOARD)
    public String dashboard() {
        return ViewResolver.ADMIN_DASHBOARD;
    }

    @GetMapping(value = Endpoints.EDITOR)
    public String editor(Model model, @RequestParam(name = JSONTemplateConst.query, defaultValue = "login", required = false) String query) throws Exception {
        String jsonData = AppInject.templateService.getJSONFromURI(query);
        String execute = AppInject.templateParser.pageDefinition(jsonData);

        model.addAttribute(JSONTemplateConst.JSONForm, execute);
        model.addAttribute(JSONTemplateConst.unProcessedJSON, jsonData);
        model.addAttribute(JSONTemplateConst.jsonList, readFiles());
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

    private List<String> readFiles() {
        File file1 = Paths.get(JSONTemplateConst.JSON_SCHEMA_ATTR).toFile();
        Collection<File> files = FileUtils.listFiles(file1,new String[]{"json"},false);
        List<String> fileNames=new ArrayList<>();
        for (File file:files) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }


}
