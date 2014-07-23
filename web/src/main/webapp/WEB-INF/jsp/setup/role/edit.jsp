<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <form name="role"
        action="setup/role/edit.do?readOnly=false">

        <input type="hidden" id="roleId" name="roleId" value="${entity.roleId}" />

        <fmt:message var="title" key="role" />
        <ats:window title="${title}">
            <table>
                <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
                <tr class="error-hidden"><td id="errors.role">&#160;</td></tr>
                <tr class="error-hidden"><td id="errors.name">&#160;</td></tr>
                <tr class="error-hidden"><td id="errors.role">&#160;</td></tr>
            </table>
            <table class="nonDataTable width50">
                <tr>
                    <td class="ats-label width5em"><fmt:message key="role.name" /></td>
                    <td>
                        <input type="text" name="name" value="${entity.name}" class="text" />
                    </td>
                    <td class="ats-label width5em"><fmt:message key="role.priority" /></td>
                    <td>
                        <input type="text" name="priority" value="${entity.priority}" class="number" />
                    </td>
                </tr>
            </table>
        </ats:window>

        <fmt:message var="title" key="role.functions" />
        <ats:window title="${title}">
            <div id="dataDiv">&#160;</div>
        </ats:window>
    </form>

    <!-- info which scripts to load -->
    <script type="text/javascript" src="js/module/setup/role/edit.js" />

</jsp:root>