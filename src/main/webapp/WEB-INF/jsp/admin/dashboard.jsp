<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>JSON Driven Template</title>
    <link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>'>
    <link rel="stylesheet" href='<c:url value="/css/json-viewer.css"/>'>
    <style>
        pre {
            outline: 1px solid #ccc;
            padding: 5px;
            margin: 5px;
        }

        .string {
            color: green;
        }

        .number {
            color: darkorange;
        }

        .boolean {
            color: blue;
        }

        .null {
            color: magenta;
        }

        .key {
            color: red;
        }

    /*    scroll bar */
        /* width */
        ::-webkit-scrollbar {
            width: 8px;
        }

        /* Track */
        ::-webkit-scrollbar-track {
            background: #f1f1f1;
        }

        /* Handle */
        ::-webkit-scrollbar-thumb {
            background: #888;
        }

        /* Handle on hover */
        ::-webkit-scrollbar-thumb:hover {
            background: #555;
        }
    </style>
</head>
<body>
<div class="row">
    <div class="col-lg-12 pr-0">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">

            <div class="col-lg-5">
                <a class="navbar-brand" href="javaScript:void(0)">
                    <img src="/images/json.png" height="32px" class="d-inline-block align-top" alt="">
                    Driven Template</a>
            </div>

            <div class="col-lg-7">
                <a href="/logout" class="mt-2 font-weight-bold text-white float-right">Log Out</a>
            </div>
        </nav>

    </div>


    <div class="col-lg-6 pr-0 pt-0" >
                <pre id="json-display" style="overflow: scroll;height: 90vh">${unProcessedJSON}</pre>
    </div>

    <div class="col-lg-6 p-0">
        <div class="col-lg-12 mt-1 mb-1">
            <button type="button" id="run" class="btn btn-primary">Run</button>
        </div>
        <div class="card">
            <div class="card-body" id="json-form">
                ${JSONForm}
            </div>
        </div>
    </div>

</div>
<!-- Javascript files-->
<script src='<c:url value="/js/jquery-3.2.1.min.js"/>'></script>
<script src='<c:url value="/js/popper.js"/>'></script>
<script src='<c:url value="/js/bootstrap.js"/>'></script>
<script src='<c:url value="/js/jquery.json-editor.min.js"/>'></script>
<script src='<c:url value="/js/json-viewer.js"/>'></script>

<script>

    $(document).ready(function () {
        try {
            var json = JSON.parse($('#json-display').text());
            $('#json-display').html("");
            new JsonEditor('#json-display', json);
        } catch (ex) {
            alert('Wrong JSON Format: ' + ex);
        }

        $('#run').bind("click",function(){
            var data=$('#json-display').text();

            $.ajax({
                type: 'POST',
                url: "/admin/dashboard",
                data: {json:data},
                success: function(resultData) {
                    $('#json-form').html(resultData);
                },
            });
        });
    });
</script>

</body>
</html>