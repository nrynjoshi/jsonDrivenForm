<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>JSON Form Builder</title>
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
<body style="background-color: #D3D3D3">
<div class="row">
    <div class="col-lg-6">
        <div class="card">
            <div class="card-header d-flex align-items-center">
                <h3 class="h4">RAW JSON Form </h3>
            </div>
            <div class="card-body">
                <div id="json-content">
                    ${unProcessedJSON}
                </div>
            </div>
        </div>
    </div>

    <div class="col-lg-6">
        <div class="card">
            <div class="card-header d-flex align-items-center">
                <h3 class="h4">JSON Form Builder</h3>
            </div>
            <div class="card-body">

                ${JSONForm}
            </div>
        </div>
    </div>

</div>
<!-- Javascript files-->
<script src='<c:url value="/js/jquery-3.2.1.min.js"/>'></script>
<script src='<c:url value="/js/popper.js"/>'></script>
<script src='<c:url value="/js/bootstrap.js"/>'></script>

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