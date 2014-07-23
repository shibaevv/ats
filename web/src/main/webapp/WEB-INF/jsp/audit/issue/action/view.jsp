<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder">
        <div id="dataCenter" class="ats-container">
            <form id="dataForm" name="dataForm">
                <input id="auditId" value="${entity.issue.audit.id}" type="hidden" />
                <input id="issueId" value="${entity.issue.id}" type="hidden" />
                <input id="actionId" value="${entity.id}" type="hidden" />
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />
            </form>
           
            <div class="ats-container">
                <div class="ats-container-hd">
                    <h2><fmt:message key="audit.title"/> - <a id="homeAudit" href="#" title="Go to report">${entity.issue.audit.name}</a></h2>
                </div>
                <div class="ats-container-hd">
                    <h2><fmt:message key="issue.title"/> - <a id="homeIssue" href="#" title="Go to issue">${entity.issue.name}</a></h2>
                </div>
                <div class="ats-container-hd">
                    <h2><fmt:message key="action.title"/> - ${entity.name}</h2>
                </div>
                <div class="ats-container-bd">
                    <table class="nonDataTable">
                        <tr>
                            <td class="width50 valign-top">
                                <table>
                                    <tr>
                                        <td>
                                            <table class="fieldset">
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="action.issue.rating"/></td>
                                                    <td>${entity.issue.rating.name}</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table class="fieldset">
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="action.responsibleUser"/></td>
                                                    <td class="left">
                                                        <ul>
                                                            <c:forEach items="${entity.responsibleUsers}" var="user" varStatus="status">
                                                                <li>${user.fullName}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="action.borm.responsible"/></td>
                                                    <td class="left">
                                                       <ul>
                                                            <c:forEach items="${entity.bormResponsibleUsers}" var="item" varStatus="status">
                                                                <li>${item.name}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="action.groupDivisions"/></td>
                                                    <td class="left">
                                                        <ul>
                                                            <c:forEach items="${entity.groupDivisions}" var="item" varStatus="status">
                                                                <li>${item.name}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <table>
                                    <tr>
                                        <td>
                                            <table class="fieldset">
                                                <tr>
                                                    <c:set var="bs" value="${entity.businessStatus}" />
                                                    <c:set var="daysOverdue" value="${entity.daysOverdue}" />
                                                    <c:set var="overDue" value="${entity.overDue}" />
                                                    <c:choose>
                                                        <c:when test="${bs.implemented}">
                                                            <th colspan="2" class="green">${bs.name}</th>
                                                            <c:set var="color" value="green" />
                                                        </c:when>
                                                        <c:when test="${bs.noLongerApplicable}">
                                                            <th colspan="2" class="grey">${bs.name}</th>
                                                            <c:set var="color" value="grey" />
                                                        </c:when>
                                                        <c:when test="${daysOverdue eq 0}">
                                                            <th colspan="2" class="blue"><fmt:message key="action.due.today"/></th>
                                                            <c:set var="color" value="blue" />
                                                        </c:when>
                                                        <c:when test="${daysOverdue lt 0}">
                                                            <th colspan="2" class="blue"><fmt:message key="action.due.in.days"><fmt:param>${-daysOverdue}</fmt:param></fmt:message></th>
                                                            <c:set var="color" value="blue" />
                                                        </c:when>
                                                        <c:when test="${daysOverdue gt 0}">
                                                            <th colspan="2" class="red"><fmt:message key="action.days.overdue"><fmt:param>${daysOverdue}</fmt:param></fmt:message></th>
                                                            <c:set var="color" value="red" />
                                                        </c:when>
                                                    </c:choose>
                                                    <th colspan="2" class="${color}">
                                                        <c:if test="${entity.editable}">
                                                            <div>
                                                                <fmt:message var="title" key="action.update.status" />
                                                                <input id="updateStatus" type="button" value="${title}" />
                                                            </div>
                                                        </c:if>
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="action.issue.due.by"/></td>
                                                    <td colspan="3"><fmt:formatDate var="dueDate" pattern="${datePattern}" value="${entity.dueDate}" />${dueDate}</td>
                                                </tr>
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="action.businessStatus"/></td>
                                                    <td>${entity.businessStatus.name}</td>
                                                    <td class="ats-label"><fmt:message key="action.business.date.closed"/></td>
                                                    <td><fmt:formatDate var="closedDate" pattern="${datePattern}" value="${entity.closedDate}" />${closedDate}</td>
                                                </tr>
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="action.followup.status"/></td>
                                                    <td>${entity.followupStatus.name}</td>
                                                    <td class="ats-label"><fmt:message key="action.followup.date"/></td>
                                                    <td><fmt:formatDate var="followupDate" pattern="${datePattern}" value="${entity.followupDate}" />${followupDate}</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <fmt:message var="title" key="action.issue.findings" />
            <ats:window title="${title}">
                <ats:text text="${entity.issue.detail}" />
            </ats:window>

            <fmt:message var="title" key="action.agreed.action" />
            <ats:window title="${title}">
                <ats:text text="${entity.detail}" />
            </ats:window>

            <fmt:message var="title" key="action.comments" />
            <ats:window title="${title}">
                <c:if test="${entity.editable}">
                    <div>
                        <fmt:message var="title" key="comment.add" />
                        <input id="addComment" type="button" value="${title}" />
                    </div>
                </c:if>                
                <div id="dataDiv">&#160;</div>                
            </ats:window>

            <fmt:message var="title" key="action.auditLogs" />
            <ats:window title="${title}" collapse="true" collapsed="true">
                <div id="dataAuditLogDiv">&#160;</div>
            </ats:window>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/audit/issue/action/view.js" />
    </div>

</jsp:root>