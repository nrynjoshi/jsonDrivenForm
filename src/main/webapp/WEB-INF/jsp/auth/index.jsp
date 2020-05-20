<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

${template}
<script>
    $(document).ready(function () {
        var $form = $('form');
        var fieldHTML = '<input type="hidden" name="uri" value="${uri}"/>';
        $($form).append(fieldHTML);
    });
</script>