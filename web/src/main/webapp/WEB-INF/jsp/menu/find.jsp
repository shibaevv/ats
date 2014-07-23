<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
<c:if test="${empty home}">
    <fmt:message var="home" key="menu.home"/>
</c:if>
<fmt:message var="myaction" key="menu.myaction"/>
<fmt:message var="action" key="menu.action"/>
<fmt:message var="audit" key="menu.report"/>
<fmt:message var="auditAdmin" key="menu.reportAdmin"/>
<fmt:message var="user" key="menu.user"/>
<fmt:message var="setup" key="menu.setup"/>
<c:if test="${empty homeUrl}">
    <c:set var="home" value="${myaction}" />
    <c:set var="homeUrl" value="${myactionUrl}" />
</c:if>
{
"menuItems":[
<c:if test="${not empty homeUrl}">
{"id":"home","text":"${home}","url":"${homeUrl}"},
</c:if>

<c:if test="${homeUrl ne myactionUrl}">
{"id":"myaction","text":"${myaction}","url":"${myactionUrl}"},
</c:if>
<c:if test="${homeUrl ne actionUrl}">
<ats:present path="${actionUrl}">
{"id":"action","text":"${action}","url":"${actionUrl}"},
</ats:present>
</c:if>
<c:if test="${homeUrl ne auditUrl}">
<ats:present path="${auditUrl}">
{"id":"audit","text":"${audit}","url":"${auditUrl}"},
</ats:present>
</c:if>
<c:if test="${homeUrl ne auditAdminUrl}">
<ats:present path="${auditAdminUrl}">
{"id":"auditAdmin","text":"${auditAdmin}","url":"${auditAdminUrl}"},
</ats:present>
</c:if>
<c:if test="${homeUrl ne userUrl}">
<ats:present path="${userUrl}">
{"id":"user","text":"${user}","url":"${userUrl}"},
</ats:present>
</c:if>
<c:if test="${homeUrl ne setupUrl}">
<ats:present path="${setupUrl}">
{"id":"setup","text":"${setup}","url":"${setupUrl}"},
</ats:present>
</c:if>

{"id":"logout","classname":"float-right","text":"Logout","title":"${username}","url":"javascript:YAHOO.ats.logout();"}
]
}
</jsp:root>