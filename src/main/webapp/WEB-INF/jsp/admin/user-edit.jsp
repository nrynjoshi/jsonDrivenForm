<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>User</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/sidebar.css">
</head>
<body>
<div class="row">
    <jsp:include page="layout/header.jsp"/>
    <jsp:include page="layout/sidebar.jsp">
        <jsp:param name="uri" value="<%= request.getRequestURI() %>"/>
    </jsp:include>

    <div class="col-lg-10">
        <div class="row">

            <div class="col-lg-12 mt-3">
                <form method="POST" action="/admin/user" autocomplete="off">
                    <input name="_id" type="text" class="form-control mb-2" id="_id" value="${template._id}" hidden>
                    <input name="password" type="text" class="form-control mb-2" id="password" value="${template.password}" hidden>
                    <div class="align-items-center">
                        <div class="col-lg-4">
                            <label for="username">Username</label>
                            <input name="username" type="text" class="form-control mb-2" id="username"
                                   placeholder="Enter your Username" required autocomplete="off" value="${template.username}">
                        </div>
                        <div class="col-lg-4">
                            <label for="fullname">Full Name</label>
                            <input name="fullname" type="text" class="form-control mb-2" id="fullname"
                                   placeholder="Enter full name" required value="${template.fullname}">
                        </div>

                        <div class="col-lg-4">
                            <label for="role">Role</label>
                            <select id="role" name="role" class="form-control mb-2" required>
                                <option disabled value="">Choose...</option>
                                <option <c:if test="${template.role eq 'Super_Admin'}">selected</c:if> value="Super_Admin">SuperAdmin</option>
                                <option <c:if test="${template.role eq 'User'}">selected</c:if> value="User">User</option>
                            </select>
                        </div>

                        <div class="col-lg-4 ml-4">
                            <input class="form-check-input" type="checkbox" name="enable" id="autoSizingCheck2" <c:if test="${template.enable eq 'on'}">checked</c:if>>
                            <label for="autoSizingCheck2">
                                Enable
                            </label>
                        </div>

                        <div class="col-lg-4">
                            <button type="submit" class="btn btn-primary mb-2">Submit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Javascript files-->
<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/popper.js"></script>
<script src="/js/bootstrap.js"></script>
<script src="/js/sidebar.js"></script>
</body>
</html>

