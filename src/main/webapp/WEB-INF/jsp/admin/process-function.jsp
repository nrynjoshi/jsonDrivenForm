<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>JSON Driven Template</title>
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
                <form method="POST" action="/admin/processfunction">
                    <div class="form-row align-items-center">
                        <div class="col-auto">
                            <label class="sr-only" for="functionName">Function Name</label>
                            <input name="name" type="text" class="form-control mb-2" id="functionName" placeholder="Function Name">
                        </div>

                        <div class="col-auto">
                            <label class="sr-only" for="accessURL">Access URL</label>
                            <input name="url" type="text" class="form-control mb-2" id="accessURL" placeholder="Access URL">
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
                        <th scope="col">Name</th>
                        <th scope="col">URL</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${functionList}" var="function">
                        <tr>
                            <td>${function.name}</td>
                            <td>${function.url}</td>
                            <td><a href="/admin/processfunction/${function._id}" class="btn btn-danger">Delete</a></td>
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
