package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.JSONTemplateConst;
import com.jsondriventemplate.constant.AppConstant;
import com.jsondriventemplate.constant.UrlConstant;

import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import java.util.Locale;

@Controller
@RequestMapping(value = UrlConstant.ADMIN)
public class TemplateManipulateController {
	@Autowired
    private MessageSource messageSource;

    @GetMapping(value = UrlConstant.DASHBOARD)
    public String dashboard() {
        return ViewResolver.ADMIN_DASHBOARD;
    }

    @GetMapping(value = UrlConstant.EDITOR)
    public String editor(Model model, @RequestParam(name = JSONTemplateConst.query, defaultValue = AppConstant.LOGIN, required = false) String query) throws Exception {
        String jsonData = AppInject.templateService.getJSONFromURI(query);
        String execute = AppInject.templateParser.pageDefinition(jsonData);

        model.addAttribute(JSONTemplateConst.JSONForm, execute);
        model.addAttribute(JSONTemplateConst.unProcessedJSON, jsonData);
        model.addAttribute(JSONTemplateConst.jsonList, readFiles());
        return ViewResolver.ADMIN_EDITOR;
    }

    @PostMapping(value = UrlConstant.EDITOR)
    public @ResponseBody
    String pageUpdate(@RequestBody MultiValueMap jsonMap) throws IOException, TemplateException {
        String jsonData = (String) jsonMap.toSingleValueMap().get(AppConstant.JSON);
        if (StringUtils.isBlank(jsonData)) {
            return messageSource.getMessage("error.employee.errordisplay", null, Locale.getDefault());
        }
        try{
           return AppInject.templateParser.pageDefinition(jsonData);
        }catch (Exception x){
            return AppInject.templateParser.execute(jsonData);
        }

    }

    private List<String> readFiles() {
        File file1 = Paths.get(JSONTemplateConst.JSON_SCHEMA_ATTR).toFile();
        Collection<File> files = FileUtils.listFiles(file1,new String[]{AppConstant.JSON},false);
        List<String> fileNames=new ArrayList<>();
        for (File file:files) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }


}
