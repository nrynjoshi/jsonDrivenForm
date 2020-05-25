package com.jsondriventemplate.controller;

final class ViewResolver {

    private static final String SEPERATOR="/";
    private static final String AUTH="auth";
    static final String INDEX="index";
    private static final String ADMIN="admin";
    private static final String EDITOR="editor";
    private static final String DASHBOARD="dashboard";
    private static final String JSON_TEMPLATE="json-template";
    private static final String JSON_TEMPLATE_EDIT="json-template-edit";
    private static final String USER="user";
    private static final String USER_EDIT="user-edit";
    private static final String PROCESS_FUNCTION="process-function";
    private static final String PROCESS_FUNCTION_EDIT="process-function-edit";
    private static final String JS_FUNCTION="js-function";
    private static final String JS_FUNCTION_EDIT="js-function-edit";

    static final String AUTH_INDEX=AUTH+SEPERATOR+INDEX;
    static final String ADMIN_INDEX=ADMIN+SEPERATOR+INDEX;
    protected static final String AUTH_DASHBOARD=AUTH+SEPERATOR+DASHBOARD;
    static final String ADMIN_EDITOR=ADMIN+SEPERATOR+EDITOR;
    static final String ADMIN_DASHBOARD=ADMIN+SEPERATOR+DASHBOARD;
    static final String ADMIN_JSON_TEMPLATE=ADMIN+SEPERATOR+JSON_TEMPLATE;
    static final String ADMIN_JSON_TEMPLATE_EDIT=ADMIN+SEPERATOR+JSON_TEMPLATE_EDIT;
    static final String ADMIN_USER=ADMIN+SEPERATOR+USER;
    static final String ADMIN_USER_EDIT=ADMIN+SEPERATOR+USER_EDIT;
    static final String ADMIN_PROCESS_FUNCTION=ADMIN+SEPERATOR+PROCESS_FUNCTION;
    static final String ADMIN_PROCESS_FUNCTION_EDIT=ADMIN+SEPERATOR+PROCESS_FUNCTION_EDIT;
    static final String ADMIN_JS_FUNCTION=ADMIN+SEPERATOR+JS_FUNCTION;
    static final String ADMIN_JS_FUNCTION_EDIT=ADMIN+SEPERATOR+JS_FUNCTION_EDIT;


}
