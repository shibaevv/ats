<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
<c:set var="dropdownGroups">[<c:forEach items="${groups}" var="item" varStatus="s">{"label":"${item.name}","value":"${item.id}"}<c:if test="${not s.last}">,</c:if></c:forEach>]</c:set>
<c:set var="dropdownDivisions">[]</c:set>
<c:set var="dropdownRoles">[<c:forEach items="${roles}" var="item" varStatus="s">{"label":"${item.name}","value":"${item.id}"}<c:if test="${not s.last}">,</c:if></c:forEach>]</c:set>
<c:set var="dropdownReportTypes">[<c:forEach items="${reportTypes}" var="item" varStatus="s">{"label":"${item.name}","value":"${item.id}"}<c:if test="${not s.last}">,</c:if></c:forEach>]</c:set>
{
"filters":[
{"name":"searchUserName","label":"Search User","valuesUrl":"filter/userName.do?1=1","forceSelection":true}
],

"actions":{"find":"user/matrix/main.do?dispatch=find&amp;user.userId=","expand":"","edit":"user/matrix/edit.do?userMatrixId=","view":"user/matrix/view.do?matrixId=","save":"user/matrix/edit.do?dispatch=save&amp;readOnly=false&amp;userMatrixId="},

"columns":[
{"key":"id","label":"","hidden":true},
{"key":"userId","label":"","hidden":true},
{"key":"groupId","label":"","hidden":true},
{"key":"group","label":"Group","sortable":true,"resizeable":true,"editor":"YAHOO.widget.DropdownCellEditor","dropdownOptions":${dropdownGroups}},
{"key":"divisionId","label":"","hidden":true},
{"key":"division","label":"Division","sortable":true,"resizeable":true,"editor":"YAHOO.widget.DropdownCellEditor","dropdownOptions":${dropdownDivisions}},
{"key":"roleId","label":"","hidden":true},
{"key":"role","label":"Role","sortable":true,"resizeable":true,"editor":"YAHOO.widget.DropdownCellEditor","dropdownOptions":${dropdownRoles}},
{"key":"reportTypeId","label":"","hidden":true},
{"key":"reportType","label":"Report Type","sortable":true,"resizeable":true,"editor":"YAHOO.widget.DropdownCellEditor","dropdownOptions":${dropdownReportTypes}},
{"key":"link","label":"","formatter":"YAHOO.widget.DataTable.formatLink"},
{"key":"deleted","label":"Delete","formatter":"YAHOO.widget.DataTable.formatCheckbox"}
],

"fields":[
"id","userId","groupId","group","divisionId","division","roleId","role","reportTypeId","reportType","link","deleted"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status" >
{"id":"${entity.id}","userId":"${entity.user.id}","groupId":"${entity.group.id}","group":"${empty entity.group.id ? 'All' : entity.group.name}","divisionId":"${entity.division.id}","division":"${empty entity.division.id ? 'All' : entity.division.name}","roleId":${entity.role.id},"role":"${entity.role.name}","reportTypeId":${entity.reportType.id},"reportType":"${entity.reportType.name}","link":"javascript:YAHOO.ats.user.matrix.edit(${entity.id});edit","deleted":${entity.deleted}}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"sortedBy":{
"key":"${sort}",
"dir":"${dir}"
}

}

</jsp:root>