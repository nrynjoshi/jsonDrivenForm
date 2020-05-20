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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping(value = Endpoints.ADMIN)
public class AdminController {

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
            previewURL="/preview/"+query;
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
           return AppInject.templateParser.pageDefinition(jsonData);
        }catch (Exception x){
            return "";
        }

    }
//----------------------------------------------------------------------------------------------------------------------

    @GetMapping(value = Endpoints.USER)
    public String user(Model model) {
        List all = AppInject.mongoClientProvider.findAll(DBConstant.USER);
        model.addAttribute("templateList", all);
        return ViewResolver.ADMIN_USER;
    }

    @PostMapping(value = Endpoints.USER)
    public String saveUser(@RequestBody MultiValueMap valueMap) {

        Map dataMap = valueMap.toSingleValueMap();
        dataMap.put("password",AppInject.passwordEncoder.encode((CharSequence) dataMap.get("password")));
        AppInject.mongoClientProvider.save(dataMap, DBConstant.USER);
        return "redirect:" + Endpoints.ADMIN + Endpoints.USER;
    }

    @GetMapping(value = Endpoints.USER + Endpoints.ID)
    public String deleteUser(@PathVariable String id) {
        AppInject.mongoClientProvider.delete(id, DBConstant.USER);
        return "redirect:" + Endpoints.ADMIN + Endpoints.USER;
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
        AppInject.mongoClientProvider.save(valueMap.toSingleValueMap(), DBConstant.TEMPLATE_INFORMATION);
        return "redirect:" + Endpoints.ADMIN + Endpoints.JSON_TEMPLATE;
    }

    @GetMapping(value = Endpoints.JSON_TEMPLATE + Endpoints.ID)
    public String deleteJsonTemplateInfo(@PathVariable String id) {
        AppInject.mongoClientProvider.delete(id, DBConstant.TEMPLATE_INFORMATION);
        return "redirect:" + Endpoints.ADMIN + Endpoints.JSON_TEMPLATE;
    }

    @PostMapping(value = Endpoints.JSON_DEFINITION)
    public @ResponseBody String saveJsonDefintion(@RequestBody MultiValueMap<String, Object> map) throws JSONValidationException {
        Map<String, Object> stringObjectMap = map.toSingleValueMap();
        String json = AppInject.jsonValidator.validJSONFormat((String) stringObjectMap.get("json"));
        stringObjectMap.put("json",json);
        AppInject.mongoClientProvider.save(stringObjectMap, DBConstant.JSON_TEMPLATE_DEFINITION);
        return "success";
    }

    //Process Function APIS
    @GetMapping(value = Endpoints.PROCESS_FUNCTION)
    public String processFunction(Model model) {
        List all = AppInject.mongoClientProvider.findAll(DBConstant.PROCESS_FUNCTION);
        model.addAttribute("functionList", all);
        return ViewResolver.ADMIN_PROCESS_FUNCTION;
    }

    @PostMapping(value = Endpoints.PROCESS_FUNCTION)
    public String saveProcessFunction(@RequestBody MultiValueMap valueMap) {
        AppInject.mongoClientProvider.save(valueMap.toSingleValueMap(), DBConstant.PROCESS_FUNCTION);
        return "redirect:" + Endpoints.ADMIN+Endpoints.PROCESS_FUNCTION;
    }

    @GetMapping(value = Endpoints.PROCESS_FUNCTION + Endpoints.ID)
    public String deleteProcessFunctiono(@PathVariable String id) {
        AppInject.mongoClientProvider.delete(id, DBConstant.PROCESS_FUNCTION);
        return "redirect:" + Endpoints.ADMIN + Endpoints.PROCESS_FUNCTION;
    }

//    JS FUNCTION APIS
    @GetMapping(value = Endpoints.JS_FUNCTION)
    public String jsFunction(Model model) {
        List all = AppInject.mongoClientProvider.findAll(DBConstant.JS_FUNCTION);
        model.addAttribute("functionList", all);
        return ViewResolver.ADMIN_JS_FUNCTION;
    }

    @PostMapping(value = Endpoints.JS_FUNCTION)
    public String saveJsFunction(@RequestBody MultiValueMap valueMap) {
        AppInject.mongoClientProvider.save(valueMap.toSingleValueMap(), DBConstant.JS_FUNCTION);
        return "redirect:" + Endpoints.ADMIN+Endpoints.JS_FUNCTION;
    }

    @GetMapping(value = Endpoints.JS_FUNCTION + Endpoints.ID)
    public String deleteJsFunctiono(@PathVariable String id) {
        AppInject.mongoClientProvider.delete(id, DBConstant.JS_FUNCTION);
        return "redirect:" + Endpoints.ADMIN + Endpoints.JS_FUNCTION;
    }
}