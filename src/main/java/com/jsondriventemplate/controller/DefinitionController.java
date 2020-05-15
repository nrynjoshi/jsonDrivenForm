package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.exception.JSONValidationException;
import com.jsondriventemplate.repo.DBConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = Endpoints.ADMIN)
public class DefinitionController {


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

}
