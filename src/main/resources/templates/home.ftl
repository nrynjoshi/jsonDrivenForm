
<form   <#if definitions.class?has_content> class="${definitions.class}" </#if>
        <#if definitions.id?has_content> id="${definitions.id}" </#if>
        <#if definitions.method?has_content> method="${definitions.method}" </#if>
        <#if definitions.action?has_content> action="${definitions.action}" </#if>
        <#if definitions.jsmethod?has_content> onsubmit="${definitions.jsmethod}" </#if>
>
    <#list definitions.fields?keys as name>
        <#assign field=definitions.fields[name]>
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
