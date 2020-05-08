<form   <#if element.definitions.class?has_content> class="${element.definitions.class}" </#if>
        <#if element.definitions.id?has_content> id="${element.definitions.id}" </#if>
        <#if element.definitions.method?has_content> method="${element.definitions.method}" </#if>
        <#if element.definitions.action?has_content> action="${element.definitions.action}" </#if>
        <#if element.definitions.jsmethod?has_content> onsubmit="${element.definitions.jsmethod}" </#if>
>
    <#list element.definitions.fields?keys as name>
        <#assign field=element.definitions.fields[name]>
        <#switch field.type>
            <#case "select" >
                CASE2
                <#break>
            <#case "button" >
                <#include "button.ftl" >
                <#break>
            <#case "submit" >
                <#include "button.ftl" >
                <#break>
            <#case "checkbox">
                CASE3
                <#break>
            <#case "radio">
                CASE3
                <#break>
            <#default>
                <#include "default.ftl" >
        </#switch>

    </#list>


</form>
