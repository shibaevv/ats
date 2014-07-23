<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"actions":{"find":"setup/role/edit.do?dispatch=findFunction&amp;roleId=","expand":"","edit":"","view":"","save":"setup/role/edit.do?readOnly=false&amp;dispatch=saveFunction&amp;roleId="},

"columns":[
{"key":"id","label":"","formatter":"YAHOO.widget.DataTable.formatCheckbox","hidden":true},
{"key":"add2role","label":"Add","formatter":"YAHOO.widget.DataTable.formatCheckbox"},
{"key":"function_name","label":"Name","sortable":true,"resizeable":true},
{"key":"function_module","label":"Module","sortable":true,"resizeable":true},
{"key":"function_query","label":"Query","sortable":true,"resizeable":true},
{"key":"home","label":"Home","formatter":"YAHOO.widget.DataTable.formatClass"},
{"key":"roleHome","label":"Home","formatter":"YAHOO.widget.DataTable.formatRadio"}
],

"fields":[
"id","add2role","function_name","function_module","function_query","home","roleHome"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":${entity.id},"add2role":${entity.add2role},"function_name":"${entity.name}","function_module":"${entity.module}","function_query":"${entity.query}","home":${entity.home},"roleHome":${entity.roleHome}}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>