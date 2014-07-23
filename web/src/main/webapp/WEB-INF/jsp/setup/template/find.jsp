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

"actions":{"find":"setup/template/main.do?dispatch=find","expand":"","edit":"setup/template/edit.do?templateId=","view":"","save":"setup/template/edit.do?readOnly=false"},

"columns":[
{"key":"id","label":"","formatter":"YAHOO.widget.DataTable.formatCheckbox","hidden":true},
{"key":"template_reference","label":"Reference","formatter":"YAHOO.widget.DataTable.formatLink","sortable":false,"resizeable":true},
{"key":"template_name","label":"Name","sortable":false,"resizeable":true},
{"key":"link","label":"Download","formatter":"YAHOO.widget.DataTable.formatLink","resizeable":false}
],

"fields":[
"id","template_reference","template_name","link"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":"${entity.id}","template_reference":"javascript:YAHOO.ats.setup.template.edit(${entity.id});${entity.reference}","template_name":"${entity.name}","link":"javascript:YAHOO.ats.setup.template.download(${entity.id});download"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>