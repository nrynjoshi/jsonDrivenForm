<#if field.class?has_content> class="${field.class}" <#else>class="form-control"</#if>
<#if field.id?has_content> id="${field.id}" </#if>
<#if field.jsvalidation?has_content> ontest="${field.jsvalidation}" </#if>
<#if field.submittransform?has_content> ontest="${field.submittransform}" </#if>
<#if field.type?has_content> type="${field.type}" </#if>
<#if field.placeholder?has_content> placeholder="${field.placeholder}" </#if>
<#if field.value?has_content> type="${field.value}" </#if>
<#if field.required?has_content> <#if field.required==true>required="required"</#if> </#if>