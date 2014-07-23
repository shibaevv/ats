<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8" />
{
"actions":{"find":"comment/main.do?dispatch=findAuditLog"},

"columns":[
{"key":"id","label":"","formatter":"YAHOO.widget.DataTable.formatCheckbox","hidden":true},
{"key":"listIndex","label":"No","sortable":true,"resizeable":false},
{"key":"detail","label":"Detail","sortable":false,"resizeable":true},
{"key":"createdDate","label":"Timestamp","sortable":true,"resizeable":false,"className":"nowrap"},
{"key":"createdBy","label":"Created By","sortable":false,"resizeable":true}
],

"fields":[
"id","listIndex","detail","createdDate","createdBy"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":"${entity.id}","listIndex":"${entity.listIndex}","detail":"${fn:replace(entity.detail, newLineChar, '&lt;br/&gt;')}","createdDate":"<fmt:formatDate pattern='${dateTimePattern}' value='${entity.createdDate}' />","createdBy":"${entity.createdBy.fullName}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>