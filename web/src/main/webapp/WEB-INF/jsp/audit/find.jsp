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
{"name":"auditName","label":"Name","valuesUrl":"filter/audit/name.do?published=true"},
{"name":"reportType.name","label":"Report Type","values":${dropdownReportTypes},"maxResultsDisplayed":"${fn:length(reportTypes)}","forceSelection":true}
],

"actions":{"find":"audit/main.do?dispatch=find","expand":"issue/main.do?dispatch=find&amp;auditId=","edit":"","view":"audit/view.do?auditId="},

"initialLoad":${initialLoad},

"columns":[
{"key":"id","label":"","hidden":true},
{"key":"audit_group_division","label":"Report Group/Division","sortable":true,"resizeable":true},
{"key":"audit_name","label":"Report Name","sortable":true,"resizeable":true},
{"key":"audit_issued_date","label":"Issued Date","sortable":true,"resizeable":false,"className":"nowrap"},
{"key":"audit_actionOpen","label":"Actions Open","formatter":"YAHOO.widget.DataTable.formatNumber","sortable":true,"resizeable":false},
{"key":"audit_actionTotal","label":"Actions Total","formatter":"YAHOO.widget.DataTable.formatNumber","sortable":true,"resizeable":false}
],

"fields":[
"id","audit_group_division","audit_name","audit_issued_date","audit_actionOpen","audit_actionTotal"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":"${entity.id}","audit_group_division":"${entity.groupDivisionAll}","audit_name":"${fn:replace(entity.name, newLineChar, '&lt;br/&gt;')}","audit_issued_date":"<fmt:formatDate pattern='${datePattern}' value='${entity.issuedDate}' />","audit_actionOpen":"${entity.actionOpen}","audit_actionTotal":"${entity.actionTotal}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
},

"paginator":{
"recordsReturned":${empty recordsReturned ? 0 : recordsReturned},
"totalRecords":${empty totalRecords ? 0 : totalRecords},
"startIndex":${empty startIndex ? 0 : startIndex},
"pageSize":${empty pageSize ? 0 : pageSize}
}

}

</jsp:root>