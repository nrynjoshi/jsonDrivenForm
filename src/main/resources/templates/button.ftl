<div class="box-footer">
    <button type="submit" name="${name}" <#include "populate.ftl" >  >
        <#if field.icon?has_content><i class="fa fa-${field.icon}"></i></#if> ${field.label}
    </button>
</div>