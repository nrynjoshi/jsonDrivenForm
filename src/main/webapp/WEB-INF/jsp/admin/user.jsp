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
                    <div class="form-row align-items-center">
                        <div class="col-auto">
                            <label class="sr-only" for="username">Username</label>
                            <input name="username" type="text" class="form-control mb-2" id="username"
                                   placeholder="Enter your Username" required autocomplete="off">
                        </div>
                        <div class="col-auto">
                            <label class="sr-only" for="password">Password</label>
                            <input name="password" type="password" class="form-control mb-2" id="password"
                                   placeholder="Enter your Password" required>
                        </div>
                        <div class="col-auto">
                            <label class="sr-only" for="fullname">Full Name</label>
                            <input name="fullname" type="text" class="form-control mb-2" id="fullname"
                                   placeholder="Enter full name" required>
                        </div>

                        <div class="col-auto">
                            <label class="sr-only" for="role">Role</label>
                            <select id="role" name="role" class="form-control mb-2" required>
                                <option selected disabled>Choose...</option>
                                <option value="Super_Admin">SuperAdmin</option>
                                <option value="User">User</option>
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
                        <th scope="col">Username</th>
                        <th scope="col">Role</th>
                        <th scope="col">Enable</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${templateList}" var="template">
                        <tr>
                            <td>${template.fullname}</td>
                            <td>${template.username}</td>
                            <td>${template.role}</td>
                            <td>${template.enable}</td>

                            <td>
                                <c:if test="${template.username ne 'superadmin'}">
                                    <a href="/admin/user/${template._id}" class="btn btn-danger">Delete</a>
                                </c:if>
                            </td>
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