<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"filters":[
],

"actions":{"find":"setup/role/main.do?dispatch=find","expand":"","edit":"setup/role/edit.do?roleId=","view":"","save":"setup/role/edit.do?readOnly=false&amp;roleId="},

"columns":[
{"key":"id","label":"","formatter":"YAHOO.widget.DataTable.formatCheckbox","hidden":true},
{"key":"role_name","label":"Name","sortable":true,"resizeable":true,"editor":"YAHOO.widget.TextboxCellEditor"},
{"key":"role_priority","label":"Priority","sortable":true,"resizeable":true,"editor":"YAHOO.widget.TextboxCellEditor"},
{"key":"link","label":"","formatter":"YAHOO.widget.DataTable.formatLink"},
{"key":"deleted","label":"Delete","formatter":"YAHOO.widget.DataTable.formatCheckbox"}
],

"fields":[
"id","role_name","role_priority","link","deleted"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":"${entity.id}","role_name":"${entity.name}","role_priority":"${entity.priority}","link":"javascript:YAHOO.ats.setup.role.edit(${entity.id});edit","deleted":${entity.deleted}}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>