<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <fmt:message var="title" key="setup.businessStatus" />
    <ats:window title="${title}">
        <div id="dataDiv">&#160;</div>
    </ats:window>

    <!-- info which scripts to load -->
    <script type="text/javascript" src="js/module/setup/businessStatus/main.js" />

</jsp:root>