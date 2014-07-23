<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:security="http://www.springframework.org/security/tags"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <c:url var="logoutUrl" value="/logout.do" />
    <fmt:message var="env" key="application.env"/>

    <div id="header">
        <span id="application-heading"><fmt:message key="application.title"/></span>

        <span class="title-details">
            User:&#160;<security:authentication property="principal.username" />
        </span>
        <span class="title-details">
            Env:&#160;${fn:toUpperCase(env)}&#160;<fmt:message key="application.version"/>
        </span>
        <span class="title-details">
            <a href="${applicationHelpUrl}" target="_blank"><fmt:message key="application.help"/></a>
        </span>
    </div>

</jsp:root>