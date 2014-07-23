<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
<c:set var="dropdownGroups">[<c:forEach items="${groups}" var="item" varStatus="s">{"label":"${item.name}","value":${item.id}}<c:if test="${not s.last}">,</c:if></c:forEach>]</c:set>
{
"filters":[
{"name":"group_name","label":"Group","values":${dropdownGroups},"maxResultsDisplayed":"${fn:length(groups)}","forceSelection":true},
{"name":"division_name","label":"Division","valuesUrl":"filter/division.do?1=1","forceSelection":true},
{"name":"searchUserName","label":"Search User","valuesUrl":"filter/userName.do?1=1","forceSelection":false}
],

"actions":{"find":"user/main.do?dispatch=find","expand":"user/view.do?user.userId=","edit":"user/edit.do?user.userId=","view":"user/view.do?user.userId="},

"columns":[
{"key":"id","label":"","formatter":"YAHOO.widget.DataTable.formatCheckbox","hidden":true},
{"key":"groupDivisions_name","label":"Group/Division","sortable":false,"resizeable":true},
{"key":"user_firstName__user_lastName","label":"Name","sortable":true,"resizeable":true}
],

"fields":[
"id","groupDivision_name","user_firstName__user_lastName"
],

"records":[<c:forEach items="${entities}" var="entity" varStatus="status">
{"id":"${entity.id}","groupDivisions_name":"${empty entity.groupDivisions ? 'All' : (entity.groupDivisions[0].name)}","user_firstName__user_lastName":"${entity.fullName}"}<c:if test="${not status.last}">,</c:if>
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