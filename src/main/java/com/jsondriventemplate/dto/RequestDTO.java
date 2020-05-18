package com.jsondriventemplate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class RequestDTO {

    @JsonProperty(value = "type",required = true)
    private String script;

    @JsonProperty(value = "uri",required = true)
    private String uri;

    @JsonProperty(value = "id",required = true)
    private String id;

    @JsonProperty(value = "data")
    private Map<String,Object> data;



    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
