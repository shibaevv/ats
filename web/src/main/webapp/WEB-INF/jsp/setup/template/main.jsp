<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <fmt:message var="title" key="setup.template" />
    <ats:window title="${title}">
        <table>
            <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.template">&#160;</td></tr>
        </table>
        <div id="dataDiv">&#160;</div>
    </ats:window>

    <!-- info which scripts to load -->
    <script type="text/javascript" src="js/module/setup/template/main.js" />

</jsp:root>