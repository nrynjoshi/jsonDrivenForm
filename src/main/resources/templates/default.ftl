<div class="form-group">
    <#if name?has_content> <label>${field.label}</label> </#if>
    <div class="input-group">
        <#if field.icon?has_content><span class="input-group-addon"> <i class="fa fa-${field.icon}"></i> </span> </#if>
        <input name="${name}" <#include "populate.ftl" > >
    </div>
</div>