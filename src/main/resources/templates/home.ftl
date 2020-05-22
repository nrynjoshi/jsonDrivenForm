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
            <@elem.templateParser template=(definitions.page.snippet) elements=elements list_value=list_value></@elem.templateParser>
        <#else >
            <@elem.templateParser template=(definitions.page.snippet) elements=elements list_value=""></@elem.templateParser>
        </#if>

    </#if>

    <#--    footer part start from here -->
    <#list layout.definitions.js_url as jsURL>
        <script src="${jsURL}"></script>
    </#list>
    <script>
        $(document).ready(function () {
            var $form = $('form');
            var fieldHTML = '<input type="hidden" name="uri" value="${uri}"/>';
            $($form).append(fieldHTML);
        });
    </script>
    </body>
    </html>
</#compress>
