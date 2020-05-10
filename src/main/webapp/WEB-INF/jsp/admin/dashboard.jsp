<%@ page import="com.jsondriventemplate.HttpUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>'>
    <link rel="stylesheet" href='<c:url value="/css/sidebar.css"/>'>
</head>
<body>
<div class="row">
    <jsp:include page="layout/header.jsp"/>
    <jsp:include page="layout/sidebar.jsp">
        <jsp:param name="uri" value="<%= HttpUtil.getURI() %>"/>
    </jsp:include>

    uri <%= HttpUtil.getURI() %>
    dashboard here
    <script src='<c:url value="/js/jquery-3.2.1.min.js"/>'></script>
    <script src='<c:url value="/js/popper.js"/>'></script>
    <script src='<c:url value="/js/bootstrap.js"/>'></script>
    <script src='<c:url value="/js/sidebar.js"/>'></script>
</div>

</body>
</html>
