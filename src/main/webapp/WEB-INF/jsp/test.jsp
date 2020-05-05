<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>JSON Form</title>
    <link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>'>
</head>
<body>

<div class="card-body">
    <form class="jsonFormPopulate"></form>
</div>
<!-- Javascript files-->
<script src='<c:url value="/js/jquery-3.2.1.min.js"/>'></script>
<script src='<c:url value="/js/underscore-min.js"/>'></script>
<script src='<c:url value="/js/popper.js"/>'></script>
<script src='<c:url value="/js/bootstrap.js"/>'></script>
<jsp:include page="jsonform.jsp"/>
</body>
</html>