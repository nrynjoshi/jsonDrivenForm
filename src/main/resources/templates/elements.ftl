<#macro body elements>
    <#list elements as elementObj>
        <#switch elementObj.definitions.type>
            <#case "form" >
                <@formBody requestData=elementObj.definitions></@formBody>
                <#break>
            <#case "table" >
                <@tableBody requestData=elementObj.definitions></@tableBody>
                <#break>
            <#case "code">
                "inner snippet detected"
                <#break >
        </#switch>
    </#list>
</#macro>

<#macro populate field>
    <#if field.class?has_content> class="${field.class}" <#else>class="form-control"</#if>
    <#if field.id?has_content> id="${field.id}" </#if>
    <#if field.jsvalidation?has_content> ontest="${field.jsvalidation}" </#if>
    <#if field.submittransform?has_content> ontest="${field.submittransform}" </#if>
    <#if field.type?has_content> type="${field.type}" </#if>
    <#if field.placeholder?has_content> placeholder="${field.placeholder}" </#if>
    <#if field.value?has_content> type="${field.value}" </#if>
    <#if field.required?has_content> <#if field.required==true>required="required"</#if> </#if>
</#macro>

<#macro tableBody requestData>
    <table <#if requestData.class?has_content> class="${requestData.class}" </#if>
            <#if requestData.id?has_content> id="${requestData.id}" </#if>
    >

    </table>
</#macro>

<#macro buttonBody field name>
    <div class="box-footer">
        <button type="submit" name="${name}" <@populate field=field ></@populate>
        <#if field.icon?has_content><i class="fa fa-${field.icon}"></i></#if> >${field.label}
        </button>
    </div>
</#macro>

<#macro default field name>
    <div class="form-group">
        <#if name?has_content> <label>${field.label}</label> </#if>
        <div class="input-group">
            <#if field.icon?has_content><span class="input-group-addon"> <i class="fa fa-${field.icon}"></i>
                </span> </#if>
            <input name="${name}" <@populate field=field ></@populate> >
        </div>
    </div>
</#macro>

<#macro formBody requestData>
    <form <#if requestData.class?has_content> class="${requestData.class}" </#if>
            <#if requestData.id?has_content> id="${requestData.id}" </#if>
            <#if requestData.method?has_content> method="${requestData.method}" </#if>
            <#if requestData.action?has_content> action="${requestData.action}" </#if>
            <#if requestData.jsmethod?has_content> onsubmit="${requestData.jsmethod}" </#if>
    >
        <#list requestData.fields?keys as name>
            <#assign field=requestData.fields[name]>
            <#switch field.type>
                <#case "select" >
                    CASE2
                    <#break>
                <#case "button" >
                    <@buttonBody field=field name=name></@buttonBody>
                    <#break>
                <#case "submit" >
                    <@buttonBody field=field name=name></@buttonBody>
                    <#break>
                <#case "checkbox">
                    CASE3
                    <#break>
                <#case "radio">
                    CASE3
                    <#break>
                <#default>
                    <@default field=field name=name></@default>
            </#switch>
        </#list>
    </form>

</#macro>



<#macro templateParser template elements>
    <#assign closingTag="">
    <#list template?split(",") as level1>
        <@small level1=level1/>
    </#list>
        <@body elements=elements ></@body>


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
