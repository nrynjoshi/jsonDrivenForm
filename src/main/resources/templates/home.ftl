<#import "./elements.ftl" as elem>
<#compress>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title>${definitions.page.title}</title>
        <#list layout.definitions.css_url as cssURL>
            <link rel="stylesheet" href="${cssURL}"/>
        </#list>
    </head>
    <body>
    <#--    main content part-->
    <#if elements??>
        <#if list_value??>
            <@elem.templateParser template=(definitions.page.snippet) elements=elements list_value=list_value uri=uri></@elem.templateParser>
        <#else >
            <@elem.templateParser template=(definitions.page.snippet) elements=elements list_value="" uri=uri></@elem.templateParser>
        </#if>

    </#if>

    <#--    footer part start from here -->
    <#list layout.definitions.js_url as jsURL>
        <script src="${jsURL}"></script>
    </#list>
    <script>
        $(document).ready(function () {
            var $form = $('form');
            var fieldHTML = '<input type="hidden" name="uri" value="${uri}"/>' + <#if _id??>'<input type="hidden" name="_id" value="${_id}"/>'
            <#else>''</#if>;
            $($form).append(fieldHTML);
            <#if _id??>
            loadData();
            </#if>
            <#if _id??>

            function loadData() {
                $.ajax({
                    url: "/auth/process/getId?uri=${uri}&id=${_id}",
                    type: 'GET',
                    dataType: 'json', // added data type
                    success: function (data) {
                        console.log(data);
                        for (var i in data) {
                            $('input[name="'+i+'"]').val(data[i]);
                        }
                        // $('form').loadJSON(data);
                    }
                });
            }

            </#if>
        });

    </script>
    </body>
    </html>
</#compress>
