package com.jsondrivenform;

public enum  StatusCode {

    NOT_FOUND("404");

    private String value;

    StatusCode(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
