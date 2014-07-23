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

"actions":{"find":"setup/businessStatus/main.do?dispatch=find","expand":"","edit":"setup/businessStatus/edit.do?businessStatusId=","view":"","save":"setup/businessStatus/edit.do?readOnly=false&amp;businessStatusId="},

"columns":[
{"key":"id","label":"","formatter":"YAHOO.widget.DataTable.formatCheckbox","hidden":true},
{"key":"name","label":"Name","sortable":true,"resizeable":true,"editor":"YAHOO.widget.TextboxCellEditor"},
{"key":"actionStatus_name","label":"Action Status","sortable":true,"resizeable":true},
{"key":"deleted","label":"Delete","formatter":"YAHOO.widget.DataTable.formatCheckbox"}
],

"fields":[
"id","name","actionStatus_name","deleted"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":"${entity.id}","name":"${entity.name}","actionStatus_name":"${entity.actionStatus.name}","deleted":${entity.deleted}}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>