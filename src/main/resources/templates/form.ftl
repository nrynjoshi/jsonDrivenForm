<form   <#if elementObj.definitions.class?has_content> class="${elementObj.definitions.class}" </#if>
        <#if elementObj.definitions.id?has_content> id="${elementObj.definitions.id}" </#if>
        <#if elementObj.definitions.method?has_content> method="${elementObj.definitions.method}" </#if>
        <#if elementObj.definitions.action?has_content> action="${elementObj.definitions.action}" </#if>
        <#if elementObj.definitions.jsmethod?has_content> onsubmit="${elementObj.definitions.jsmethod}" </#if>
>
    <#list elementObj.definitions.fields?keys as name>
        <#assign field=elementObj.definitions.fields[name]>
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
