<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src='<c:url value="/js/underscore-min.js"/>'></script>
<script src='<c:url value="/js/jsonform.min.js"/>'></script>
<script type="text/javascript">
    $( document ).ready(function() {
        $('.jsonFormPopulate').jsonForm({
            schema: ${json_form_defination},
            onSubmit: function (errors, values) {
                if (errors) {
                    $('#res').html('<p>I beg your pardon?</p>');
                }
                else {
                    $('#res').html('<p>Hello ' + values.name + '.' +
                        (values.age ? '<br/>You are ' + values.age + '.' : '') +
                        '</p>');
                }
            }
        });
    });

</script>