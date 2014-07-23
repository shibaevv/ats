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

"actions":{"find":"setup/function/main.do?dispatch=find","expand":"","edit":"setup/function/edit.do?functionId=","view":"","save":"setup/function/edit.do?readOnly=false&amp;functionId="},

"columns":[
{"key":"id","label":"","formatter":"YAHOO.widget.DataTable.formatCheckbox","hidden":true},
{"key":"function_name","label":"Name","sortable":true,"resizeable":true,"editor":"YAHOO.widget.TextboxCellEditor"},
{"key":"function_module","label":"Module","sortable":true,"resizeable":true,"editor":"YAHOO.widget.TextboxCellEditor"},
{"key":"function_query","label":"Query","sortable":true,"resizeable":true,"editor":"YAHOO.widget.TextboxCellEditor"},
{"key":"function_home","label":"Home","formatter":"YAHOO.widget.DataTable.formatCheckbox"}
],

"fields":[
"id","function_name","function_module","function_query","function_home"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":"${entity.id}","function_name":"${entity.name}","function_module":"${entity.module}","function_query":"${entity.query}","function_home":${entity.home}}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>