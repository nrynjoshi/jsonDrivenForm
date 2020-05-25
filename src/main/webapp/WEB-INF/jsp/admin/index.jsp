<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/sidebar.css">
</head>
<body>
<div class="row">
    <jsp:include page="layout/header.jsp"/>
    <jsp:include page="layout/sidebar.jsp">
        <jsp:param name="uri" value=""/>
    </jsp:include>
    <div class="col-lg-10">
        ${template}
    </div>

    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/popper.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script src="/js/sidebar.js"></script>

    <script>
        $(document).ready(function () {
            var $form = $('form');
            var fieldHTML = '<input type="hidden" name="uri" value="${uri}"/>' <c:if test="${not empty _id}"> +'<input type="hidden" name="_id" value="${_id}"/>'
            </c:if>;
            $($form).append(fieldHTML);
            <c:if test="${not empty _id}">
            loadData();
            </c:if>

            <c:if test="${not empty _id}">

            function loadData() {
                $.ajax({
                    url: "/admin/process/getId?uri=${uri}&id=${_id}",
                    type: 'GET',
                    dataType: 'json', // added data type
                    success: function (data) {
                        for (var i in data) {
                            $('input[name="' + i + '"]').val(data[i]);
                        }
                        // $('form').loadJSON(data);
                    }
                });
            }

            </c:if>
            <c:if test="${not empty type && type eq 'search'}">
            $('button[type="submit"]').on('click', function (e) { //use on if jQuery 1.7+
                e.preventDefault();  //prevent form from submitting
                var data = $("form :input").serializeArray();
                console.log(data); //use the console for debugging, F12 in Chrome, not alerts
                $.ajax({
                    url: "/admin/process/search",
                    type: 'POST',
                    data: data,
                    success: function (res) {
                        $('#searchList').html(res);
                    }
                });
            });

            </c:if>
        });

    </script>
</div>

</body>
</html>

