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
                <form method="POST" action="/admin/user">
                    <div class="form-row align-items-center">
                        <div class="col-auto">
                            <label class="sr-only" for="fullname">Full Name</label>
                            <input name="fullname" type="text" class="form-control mb-2" id="fullname" placeholder="Enter full name">
                        </div>

                        <div class="col-auto">
                            <label class="sr-only" for="role">Role</label>
                            <select id="role" name="role" class="form-control mb-2">
                                <option selected>Choose...</option>
                                <option value="superAdmin">SuperAdmin</option>
                                <option value="admin">Admin</option>
                                <option value="user">user</option>
                            </select>
                        </div>

                        <div class="col-auto ml-4">
                                <input class="form-check-input" type="checkbox" name="enable" id="autoSizingCheck2">
                                <label for="autoSizingCheck2">
                                    Enable
                                </label>
                        </div>

                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary mb-2">Submit</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="col-lg-12">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Full Name</th>
                        <th scope="col">Role</th>
                        <th scope="col">Enable</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${templateList}" var="template">
                        <tr>
                            <td>${template.fullname}</td>
                            <td>${template.role}</td>
                            <td>${template.enable}</td>
                            <td><a href="/admin/user/${template._id}" class="btn btn-danger">Delete</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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