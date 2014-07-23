<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">

    <form action="auditAdmin/edit.do?dispatch=cancel">
        <input type="hidden" id="auditId" name="auditId" value="${auditId}" />
        <input type="hidden" id="submit" title="OK" />
    </form>

    <h1 class="ats-warning">
        <fmt:message key="cancel"/>
    </h1>

</jsp:root>