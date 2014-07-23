<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"filters":[
],

"actions":{"find":"myaction/main.do?dispatch=find","expand":"comment/main.do?dispatch=find&amp;actionId=","edit":"","view":"action/view.do?actionId="},

"initialLoad":${initialLoad},

"columns":[
{"key":"id","label":"","hidden":true},
{"key":"audit_name","label":"Report Title","sortable":true,"resizeable":true},
{"key":"issue_name","label":"Issue Title","sortable":true,"resizeable":true},
{"key":"rating_name","label":"Rating","sortable":true,"resizeable":true},
{"key":"action_list_index","label":"Action No.","sortable":true,"resizeable":false},
{"key":"action_detail","label":"Agreed Action","sortable":false,"resizeable":true},
{"key":"action_due_date","label":"Due Date","sortable":true,"resizeable":false,"className":"nowrap"},
{"key":"business_status_name","label":"Business Status","sortable":true,"resizeable":true},
{"key":"action_last_comment","label":"Most Recent Comment","sortable":false,"resizeable":true},
{"key":"action_closed_date","label":"Date Closed","sortable":true,"resizeable":true,"hidden":${actionStatusId eq 1}},
{"key":"link","label":"Update","formatter":"YAHOO.widget.DataTable.formatLink","resizeable":false},
{"key":"formatRowClass","label":"","hidden":true}
],

"fields":[
"id","audit_name","issue_name","rating_name","action_list_index","action_detail","action_due_date","business_status_name","action_last_comment","action_closed_date","link","formatRowClass"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":"${entity.id}","audit_name":"${entity.issue.audit.name}","issue_name":"<ats:text text='${entity.issue.name}' json='true'/>","rating_name":"${entity.issue.rating.name}","action_list_index":"${entity.listIndex}","action_detail":"<ats:crop text='${entity.detail}' json='true'/>","action_due_date":"<fmt:formatDate pattern='${datePattern}' value='${entity.dueDate}' />","business_status_name":"${entity.businessStatus.name}","action_last_comment":"<ats:crop text='${entity.comment.detail}' json='true'/>","action_closed_date":"<fmt:formatDate pattern='${datePattern}' value='${entity.closedDate}' />","link":"<c:if test='${entity.editable}'>javascript:YAHOO.ats.myaction.addComment(${entity.id});Update</c:if>","formatRowClass":"${entity.overDue ? 'ats-error' : ''}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>