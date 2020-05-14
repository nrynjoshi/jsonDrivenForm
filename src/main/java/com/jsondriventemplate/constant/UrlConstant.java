package com.jsondriventemplate.constant;

import org.springframework.http.HttpMethod;

public final class UrlConstant {
    public static final String CONTEXT="";
    public static final String ADMIN=CONTEXT+"/admin";
    public static final String EDITOR="/editor";
    public static final String JSON_TEMPLATE="/jsontemplate";
    public static final String ID="/{id}";
    public static final String DASHBOARD="/dashboard";
    public static final String LOGIN="/login";
    public static final String URI="/{uri}";
    public static final String AUTH="/auth";
	public static final String URL_LOGIN = "login";
	public static final String URL = "url";
	
	public static final String URL_ROOT="/";
	public static final String TEMPLATES = "/templates/**";
	public static final String ADMINS = "/admin/**";
	public static final String AUTHS = "/auth/*";


}
