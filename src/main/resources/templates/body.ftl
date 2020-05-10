<#list elements as elementObj>
    <#switch elementObj.definitions.type>
        <#case "form" >
            <#include "form.ftl" >
            <#break>
        <#case "table" >
            <#include "table.ftl">
            <#break>
    </#switch>
</#list>


