<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder">
        <div id="dataCenter" class="ats-container">
            <c:set var="title"><fmt:message key="audit.title"/> - ${entity.name}</c:set>
            <ats:window title="${title}">
                <form id="dataForm" name="dataForm">
                    <input id="auditId" value="${entity.id}" type="hidden" />
                    <input id="dir" name="dir" value="${dir}" type="hidden" />
                    <input id="sort" name="sort" value="${sort}" type="hidden" />
                </form>
                <table class="nonDataTable">
                    <c:if test="${not empty entity.document}">
                    <tr>
                        <td class="ats-label"><fmt:message key="audit.document"/></td>
                        <td>
                            <c:if test="${not empty entity.document.id}">
                                <a href="javascript:YAHOO.ats.downloadReport(${entity.id},${entity.document.id});" title="Download ${entity.document.name}">
                                    <img src="css/img/ext/${entity.document.ext}.gif" />&#160;${entity.document.name}
                                </a>
                            </c:if>
                        </td>
                    </tr>
                    </c:if>
                    <tr>
                        <td class="ats-label"><fmt:message key="audit.issuedDate"/></td>
                        <td><fmt:formatDate pattern="${datePattern}" value="${entity.issuedDate}" /></td>
                    </tr>
                    <tr>
                        <td class="ats-label"><fmt:message key="audit.groupDivisions"/></td>
                        <td class="left">
                            <ul>
                                <c:forEach items="${entity.groupDivisions}" var="item" varStatus="status">
                                    <li>${item.name}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </table>
            </ats:window>

            <fmt:message var="title" key="audit.issues.actions" />
            <ats:window title="${title}">
                <ats:winoption id="issueHelp" url="${issueHelpUrl}"><fmt:message key="audit.issues.actions.help" /></ats:winoption>
                <div id="dataDiv">&#160;</div>
            </ats:window>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/audit/view.js" />
    </div>

</jsp:root>