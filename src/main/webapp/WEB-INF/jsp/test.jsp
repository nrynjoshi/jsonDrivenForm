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
    <style>
        pre {outline: 1px solid #ccc; padding: 5px; margin: 5px; }
        .string { color: green; }
        .number { color: darkorange; }
        .boolean { color: blue; }
        .null { color: magenta; }
        .key { color: red; }
    </style>
</head>
<body>

<div class="Container">
    <div class="row" style="height: 100%;">
        <div class="col-md-2" style="background-color: #e8e8e8">


        </div>
        <div class="col-md-6">
            <div class="card-body">
                <form class="jsonFormPopulate" action="/" data-jsonURI="" method="POST"></form>
            </div>
        </div>
        <div class="col-md-4" style="background-color: #141414;color: white;">

            <div id="json-content">
                {
                ${form_schema_defination}
                <c:if test="${not empty form_defination}">
                    ,form:${form_defination}
                </c:if>
                <c:if test="${not empty form_value}">
                    ,value:${form_value},
                </c:if>
                }
            </div>
        </div>
    </div>
</div>

<!-- Javascript files-->
<script src='<c:url value="/js/jquery-3.2.1.min.js"/>'></script>
<script src='<c:url value="/js/underscore-min.js"/>'></script>
<script src='<c:url value="/js/popper.js"/>'></script>
<script src='<c:url value="/js/bootstrap.js"/>'></script>
<jsp:include page="jsonform.jsp"/>
<script>
    $(document).ready(function(){
        var prettyJSON=syntaxHighlight();
        $('#json-content').html(prettyJSON)
        function syntaxHighlight() {
            var json=document.getElementById("json-content").innerText;
            if (typeof json != 'string') {
                json = JSON.stringify(json, undefined, 2);
            }
            json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
            return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
                var cls = 'number';
                if (/^"/.test(match)) {
                    if (/:$/.test(match)) {
                        cls = 'key';
                    } else {
                        cls = 'string';
                    }
                } else if (/true|false/.test(match)) {
                    cls = 'boolean';
                } else if (/null/.test(match)) {
                    cls = 'null';
                }
                return '<span class="' + cls + '">' + match + '</span>';
            });
        }

    });

</script>
</body>
</html>