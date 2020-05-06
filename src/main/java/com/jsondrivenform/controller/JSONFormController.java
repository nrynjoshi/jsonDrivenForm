package com.jsondrivenform.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsondrivenform.repository.Record;
import com.jsondrivenform.repository.RecordRepository;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
public class JSONFormController {

    /**
     * [@link https://jsonform.github.io/jsonform/]
     * [@link https://jsonform.github.io/jsonform/playground/index.html?example=previousvalues]
     *
     * @param model
     * @return
     * @throws IOException
     */

    @GetMapping
    public String jsonForm(Model model) throws IOException {
        model.addAllAttributes(response());
        return "test";
    }

    @Autowired
    RecordRepository recordRepository;

    @PostMapping
    public String uploadJSON(@RequestBody MultiValueMap valueMap, RedirectAttributes redirectAttributes) throws IOException {
        Record record=new Record();
        record.putAll(valueMap.toSingleValueMap());
        recordRepository.save(record);
        //form value set
        Map<String,String> map=new HashMap<>();
        map.put("Name",valueMap.toSingleValueMap().get("Name").toString());
        JSONObject json = new JSONObject(map);
        redirectAttributes.addFlashAttribute("form_value",json.toString());
        return "redirect:/";
    }

    //----------------------------------------------------------------------
    private Map<String, Object> response() throws IOException {
        String json_defination = FileUtils.readFileToString(Paths.get("json-schema", "form.json").toFile(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(json_defination);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("form_schema_defination", actualObj.get("schema"));
        objectMap.put("form_defination", actualObj.get("form"));
        return objectMap;

    }

}
