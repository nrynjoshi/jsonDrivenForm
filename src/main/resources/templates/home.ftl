
<#compress>
    <#include "layout/header.ftl">
    <@templateParser template=definitions.snippet>

    </@templateParser>
    <#include "layout/footer.ftl">
</#compress>

<#--This code will be saved on snippet.ftl after finding its include way -->
<#macro templateParser template>
    <#assign closingTag="">
    <#list template?split(",") as level1>
        <@small level1=level1/>
    </#list>
    <#include "body.ftl" >
    ${closingTag}
</#macro>

<#macro small level1>
    <#assign items=level1?split(">")>

    <#list items as level2>
        <#assign level3=level2?replace('div|span|h5', '', 'r')>
        <#if level2?starts_with("div")>
            <@helper tag="div" data=level3 />
            <#if level2?index==items?size-1></div><#else><#assign closingTag=closingTag+"</div> "></#if>
        <#elseif level2?starts_with("span")>
            <@helper tag="span" data=level3 />
            <#if level2?index==items?size-1></span><#else><#assign closingTag=closingTag+"</span> "></#if>
        <#elseif level2?starts_with("p") >
            <@helper tag="p" data=level3 />
            <#if level2?index==items?size-1></p><#else><#assign closingTag=closingTag+"</p> "></#if>
        <#elseif level2?starts_with("a") >
            <@helper tag="a" data=level3 />
            <#if level2?index==items?size-1></a><#else><#assign closingTag=closingTag+"</a> "></#if>
        <#elseif level2?starts_with("h5") >
            <@helper tag="h5" data=level3 />
            <#if level2?index==items?size-1></h5><#else><#assign closingTag=closingTag+"</h5> "></#if>
        </#if >
    </#list>
</#macro>


<#macro helper tag data>
    <#if data?contains("[") >
        <#assign attribute=((data?keep_after("["))?keep_before("]"))>
        <#else>
            <#assign attribute="">
    </#if>
    <#assign clz=(data?keep_before("["))>
    <${tag} class="<#list clz?split(".") as clz>${clz} </#list>"
    <#if attribute?has_content >${attribute}</#if>
    > <#if attribute?has_content >${attribute}</#if>




</#macro>




