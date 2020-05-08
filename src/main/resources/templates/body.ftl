
<#switch element.definitions.type>
    <#case "form" >
        <#include "form.ftl" >
        <#break>
    <#case "table" >
        <#include "table.ftl">
        <#break>
</#switch>

