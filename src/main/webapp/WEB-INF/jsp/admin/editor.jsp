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
    <link rel="stylesheet" href="/css/json-viewer.css">
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

        .btn-op {
            padding: 2px !important;
            color: #444 !important;
            background-color: #D3D3D3 !important;
            border-color: unset;
            border: 1px solid #444;
            box-shadow: 0px 2px 0 #444, 2px 4px 6px #444;
            transition: all 150ms linear;
        }

        .btn-op:hover {
            background: #444;
            border: 1px solid #D3D3D3;
            box-shadow: 1px 1px 2px #D3D3D3;
            color: #D3D3D3;
            text-decoration: none;
            transition: all 250ms linear;
        }
    </style>
</head>
<body>
<div class="row">
    <jsp:include page="layout/header.jsp"/>
    <jsp:include page="layout/sidebar.jsp">
        <jsp:param name="uri" value="<%= request.getRequestURI() %>"/>
    </jsp:include>

    <div class="col-lg-10">
        <div class="row">

            <div class="col-lg-12 m-1 p-0">
                <select class="custom-select" required>
                    <option value="">Select JSON Template</option>
                    <c:forEach items="${jsonList}" var="item">
                        <option value="${item}">${item}</option>
                    </c:forEach>
                </select>

                <a href="/auth/login" class="btn btn-primary btn-op modalDisplay">Create</a>
                <a href="/auth/login" class="btn btn-primary btn-op modalDisplay">Save</a>
                <button type="button" id="run" class="btn btn-primary btn-op">Execute</button>
                <a href="/auth/login" class="btn btn-primary btn-op" target="_blank">Preview</a>




            </div>
            <div class="col-lg-6 pl-0 pr-0 pt-0">
                <pre id="json-display" style="overflow: scroll;height: 81vh">${unProcessedJSON}</pre>
            </div>

            <div class="col-lg-6 p-0">
                <h6>Display Board</h6>
                <div class="" id="json-form">
                    <iframe src="/auth/login" style="overflow: scroll;height: 65vh; width: 100%">

                    </iframe>

                </div>
            </div>
        </div>


    </div>


</div>
<!-- Javascript files-->
<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/popper.js"></script>
<script src="/js/bootstrap.js"></script>
<script src="/js/sidebar.js"></script>
<script src="/js/jquery.json-editor.min.js"></script>
<script src="/js/json-viewer.js"></script>

<script>

    $(document).ready(function () {
        try {
            var json = JSON.parse($('#json-display').text());
            $('#json-display').html("");
            new JsonEditor('#json-display', json);
        } catch (ex) {
            alert('Wrong JSON Format: ' + ex);
        }

        $('#run').bind("click", function () {
            var data = $('#json-display').text();

            $.ajax({
                type: 'POST',
                url: "/admin/editor",
                data: {json: data},
                success: function (resultData) {
                    $('#json-form').html(resultData);
                },
            });
        });

    });


</script>
<jsp:include page="modal.jsp"></jsp:include>
</body>
</html>