<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">

    <form action="auditAdmin/delete.do?readOnly=false">
        <input type="hidden" id="auditId" name="auditId" value="${entity.id}" />
        <input type="hidden" id="submit" title="OK" />
    </form>

    <table class="nonDataTable">
        <tr>
            <td class="ats-h1"><fmt:message key="audit.title" /></td>
            <td class="left">${entity.name}</td>
        </tr>
    </table>

    <br/>

    <h1 class="ats-warning">
        <fmt:message key="delete.audit.confirm.message"/>
    </h1>

    <br/>

    <table class="nonDataTable">
        <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
        <tr class="error-hidden"><td id="errors">&#160;</td></tr>
        <tr class="error-hidden"><td id="errors.report">&#160;</td></tr>
    </table>
    
</jsp:root>