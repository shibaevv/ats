<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">

    <form action="auditAdmin/edit.do?dispatch=publish&amp;readOnly=false">
        <input type="hidden" id="auditId" name="auditId" value="${entity.id}" />
        <input type="hidden" id="submit" title="Publish Now" />
    </form>

    <h1 class="ats-warning">
        <fmt:message key="publish.message">
            <fmt:param>${entity.name}</fmt:param>
        </fmt:message>
    </h1>

    <br/>

    <table class="nonDataTable">
        <tr class="error-hidden"><td id="errors">&#160;</td></tr>
    </table>

</jsp:root>