<#macro body elements>
    <#if elements??>
        <#list elements as elementObj>
            <#if elementObj.definitions.type??>
                <#switch elementObj.definitions.type>
                    <#case "form" >
                        <@formBody requestData=elementObj.definitions></@formBody>
                        <#break>
                    <#case "table" >
                        <@tableBody requestData=elementObj.definitions></@tableBody>
                        <#break>
                    <#case "code">
                        <#local closingTag="">
                        <#list elementObj.definitions.snippet?split(",") as level1>
                            <@small level1=level1/>
                        </#list>
                        <#if elementObj.definitions.elements??>
                                <@body elements=elementObj.definitions.elements ></@body>
                        </#if>

                    <#list elementObj.definitions.snippet?split(",") as level1>
                        ${endingTagIdentifer(level1)}
                    </#list>
                        <#break >
                </#switch>
            </#if>
        </#list>
    </#if>

</#macro>

<#macro populate field>
    <#if field.class?has_content> class="${field.class}" <#else>class="form-control"</#if>
    <#if field.id?has_content> id="${field.id}" </#if>
    <#if field.jsvalidation?has_content> ontest="${field.jsvalidation}" </#if>
    <#if field.submittransform?has_content> ontest="${field.submittransform}" </#if>
    <#if field.type?has_content> type="${field.type}" </#if>
    <#if field.placeholder?has_content> placeholder="${field.placeholder}" </#if>
    <#if field.value?has_content> value="${field.value}" </#if>
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

<#macro selectBody field name>
    <div class="form-group">
        <#if name?has_content> <label>${field.label}</label> </#if>
        <div class="input-group">
            <#if field.icon?has_content><span class="input-group-addon"> <i class="fa fa-${field.icon}"></i> </span> </#if>
            <select name="${name}" <@populate field=field ></@populate> >
                <#list field["list"]?keys as groupKey>
                    <option <#if field.value==groupKey>selected</#if> value='${(groupKey!"")}' >${field["list"][groupKey]}</option>
                </#list>
            </select>
        </div>
    </div>
</#macro>

<#macro checkBoxBody field name>
    <div class="form-group">
        <input name="${name}" <@populate field=field ></@populate> <#if field.checkvalue?has_content><#if field.checkvalue==true>checked</#if></#if> >
        <#if name?has_content> <label class="form-check-label" >${field.label} </label> </#if>
    </div>
</#macro>

<#macro radioButton field name>
    <div class="form-group">
        <input name="${field.radioname}" <@populate field=field ></@populate> <#if field.checkvalue?has_content><#if field.checkvalue==true>checked</#if></#if> >
        <#if name?has_content> <label class="form-check-label" >${field.label}</label> </#if>
    </div>
</#macro>

<#macro default field name>
    <div class="form-group">
        <#if name?has_content> <label>${field.label}</label> </#if>
        <div class="input-group">
            <#if field.icon?has_content><span class="input-group-addon"> <i class="fa fa-${field.icon}"></i>
                </span> </#if>
            <input name="${name}" <@populate field=field ></@populate> />
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
                    <@selectBody field=field name=name></@selectBody>
                    <#break>
                <#case "button" >
                    <@buttonBody field=field name=name></@buttonBody>
                    <#break>
                <#case "submit" >
                    <@buttonBody field=field name=name></@buttonBody>
                    <#break>
                <#case "checkbox">
                    <@checkBoxBody field=field name=name></@checkBoxBody>
                    <#break>
                <#case "radio">
                    <@radioButton field=field name=name></@radioButton>
                    <#break>
                <#default>
                    <@default field=field name=name></@default>
            </#switch>
        </#list>
    </form>

</#macro>

<#macro templateParser template elements>
    <#local closingTag="">
    <#list template?split(",") as level1>
        <@small level1=level1/>
    </#list>
    <@body elements=elements ></@body>
    <#list template?split(",") as level1>
        ${endingTagIdentifer(level1)}
    </#list>
</#macro>

<#function endingTagIdentifer level1>
 <#local localTag="">
    <#assign items=level1?split(">")>
    <#list items as level2>
        <#assign level3=level2?replace('div|span|h5', '', 'r')>
        <#if level2?starts_with("div")>
            <#if level2?index==items?size-1><#else><#local  localTag+="</div> "></#if>
        <#elseif level2?starts_with("span")>
            <#if level2?index==items?size-1><#else><#local  localTag+="</span> "></#if>
        <#elseif level2?starts_with("p") >
            <#if level2?index==items?size-1><#else><#local  localTag+="</p> "></#if>
        <#elseif level2?starts_with("a") >
            <#if level2?index==items?size-1><#else><#local  localTag+="</a> "></#if>
        <#elseif level2?starts_with("h5") >
            <#if level2?index==items?size-1><#else><#local localTag+="</h5> "></#if>
        </#if >
    </#list>
    <#return localTag>
</#function>

<#macro small level1>
    <#assign items=level1?split(">")>
    <#list items as level2>
        <#assign level3=level2?replace('div|span|h5', '', 'r')>
        <#if level2?starts_with("div")>
            <@helper tag="div" data=level3 />
            <#if level2?index==items?size-1></div></#if>
        <#elseif level2?starts_with("span")>
            <@helper tag="span" data=level3 />
            <#if level2?index==items?size-1></span></#if>
        <#elseif level2?starts_with("p") >
            <@helper tag="p" data=level3 />
            <#if level2?index==items?size-1></p></#if>
        <#elseif level2?starts_with("a") >
            <@helper tag="a" data=level3 />
            <#if level2?index==items?size-1></a></#if>
        <#elseif level2?starts_with("h5") >
            <@helper tag="h5" data=level3 />
            <#if level2?index==items?size-1></h5></#if>
        </#if >
    </#list>
<#--    "=========== : "${localTag} " : ==========="-->
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
