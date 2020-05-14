package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.constant.UrlConstant;
import com.jsondriventemplate.repo.DBConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = UrlConstant.ADMIN)
public class DefinitionController {


    @GetMapping(value = UrlConstant.JSON_TEMPLATE)
    public String jsonTemplate(Model model) {
        List all = AppInject.mongoClientProvider.findAll(DBConstant.TEMPLATE_INFORMATION);
        model.addAttribute("templateList",all);
        return ViewResolver.ADMIN_JSON_TEMPLATE;
    }

    @PostMapping(value = UrlConstant.JSON_TEMPLATE)
    public String saveJsonTemplateInfo(@RequestBody MultiValueMap valueMap) {
        AppInject.mongoClientProvider.save(valueMap.toSingleValueMap(),DBConstant.TEMPLATE_INFORMATION);
        return "redirect:" + UrlConstant.ADMIN + UrlConstant.JSON_TEMPLATE;
    }

    @GetMapping(value = UrlConstant.JSON_TEMPLATE + UrlConstant.ID)
    public String deleteJsonTemplateInfo(@PathVariable String id) {
        AppInject.mongoClientProvider.delete(id,DBConstant.TEMPLATE_INFORMATION);
        return "redirect:" + UrlConstant.ADMIN + UrlConstant.JSON_TEMPLATE;
    }

}
