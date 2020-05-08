<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${definitions.title}</title>
    <#list layout.definitions.css_url as cssURL>
        <link rel="stylesheet" href="${cssURL}">
    </#list>
</head>
<body <#if definitions.body.css?has_content> <label>${definitions.body.css}</label> </#if> >

