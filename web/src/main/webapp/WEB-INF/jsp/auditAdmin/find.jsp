<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
<c:set var="dropdownReportTypes">[<c:forEach items="${reportTypes}" var="item" varStatus="s">{"label":"${item.name}","value":"${item.id}"}<c:if test="${not s.last}">,</c:if></c:forEach>]</c:set>

{
"filters":[
{"name":"audit_name","label":"Name","valuesUrl":"filter/audit/name.do?published=true","forceSelection":true}
],

"actions":{"find":"auditAdmin/main.do?dispatch=find","expand":"auditAdmin/issue/main.do?dispatch=find&amp;auditId=","edit":"auditAdmin/edit.do?auditId=","view":"auditAdmin/view.do?auditId="},

"columns":[
{"key":"id","label":"","hidden":true},
{"key":"audit_name","label":"Report Title","sortable":true,"resizeable":true},
{"key":"audit_updated_date","label":"Last Modified","sortable":true,"resizeable":false},
{"key":"audit_issueUnpublished","label":"Unpublished Issues","formatter":"YAHOO.widget.DataTable.formatNumber","sortable":true,"resizeable":false},
{"key":"audit_actionUnpublished","label":"Unpublished Actions","formatter":"YAHOO.widget.DataTable.formatNumber","sortable":true,"resizeable":false}
],

"fields":[
"id","audit_name","audit_updated_date","audit_issueUnpublished","audit_actionUnpublished"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":"${entity.id}","audit_name":"${fn:replace(entity.name, newLineChar, '&lt;br/&gt;')}","audit_updated_date":"<fmt:formatDate pattern='${datePattern}' value='${entity.updatedDate}' />","audit_issueUnpublished":"${entity.issueUnpublished}","audit_actionUnpublished":"${entity.actionUnpublished}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>