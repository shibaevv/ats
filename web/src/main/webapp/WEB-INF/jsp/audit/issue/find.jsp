<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"actions":{"find":"issue/main.do?dispatch=find&amp;auditId=","expand":"action/main.do?dispatch=find&amp;issueId=","edit":"","view":"issue/view.do?issueId=","viewAction":"action/view.do?actionId="},

"columns":[
{"key":"id","label":"","hidden":true},
{"key":"issue_listIndex","label":"Issue No.","sortable":true,"resizeable":false},
{"key":"issue_name","label":"Issue Title","sortable":true,"resizeable":true},
{"key":"issue_rating_name","label":"Rating","sortable":true,"resizeable":false},
{"key":"actionId","label":"","hidden":true},
{"key":"listIndex","label":"Action No.","sortable":true,"resizeable":false},
{"key":"detail","label":"Agreed Action","sortable":false,"resizeable":true},
{"key":"dueDate","label":"Due Date","sortable":true,"resizeable":false,"className":"nowrap"},
{"key":"businessStatus_name","label":"Business Status","sortable":true,"resizeable":true},
{"key":"comment","label":"Most Recent Comment","sortable":false,"resizeable":true},
{"key":"closedDate","label":"Date Closed","sortable":true,"resizeable":false,"className":"nowrap"},
{"key":"link","label":"Update","formatter":"YAHOO.widget.DataTable.formatLink","resizeable":false}
],

"fields":[
"id","issue_listIndex","issue_name","issue_rating_name","actionId","listIndex","detail","dueDate","businessStatus_name","comment","closedDate","link"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status" >
{"id":"${entity.issue.id}","issue_listIndex":"${entity.issue.listIndex}","issue_name":"<ats:text text='${entity.issue.name}' json='true'/>","issue_rating_name":"${entity.issue.rating.name}","actionId":"${entity.id}","listIndex":"${entity.listIndex}","detail":"<ats:crop text='${entity.detail}' json='true'/>","dueDate":"<fmt:formatDate pattern='${datePattern}' value='${entity.dueDate}' />","businessStatus_name":"${entity.businessStatus.name}","comment":"<ats:crop text='${entity.comment.detail}' json='true'/>","closedDate":"<fmt:formatDate pattern='${datePattern}' value='${entity.closedDate}' />","link":"<c:if test='${entity.editable}'>javascript:YAHOO.ats.addComment(${entity.id});Update</c:if>"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>