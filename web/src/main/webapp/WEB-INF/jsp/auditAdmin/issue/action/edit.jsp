<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder" class="auditAdmin">
        <div id="dataCenter" class="ats-container">
            <form id="actionForm" name="actionForm"
                  action="actionAdmin/edit.do?readOnly=false">

                <input id="auditId" name="auditId" value="${entity.issue.audit.id}" type="hidden" />
                <input id="issueId" name="issueId" value="${entity.issue.id}" type="hidden" />
                <input id="actionId" name="actionId" value="${entity.id}" type="hidden" />
                <input id="pageSize" name="pageSize" value="${pageSize}" type="hidden" />
                <input id="startIndex" name="startIndex" value="${startIndex}" type="hidden" />
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />

                <div id="toolbarDiv">&#160;</div>
                <span></span>

                <table>
                    <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
                    <tr class="error-hidden"><td id="errors">&#160;</td></tr>
                </table>

                <div id="actionData" class="ats-container">
                    <div class="ats-container-hd">
                        <h2><fmt:message key="audit.title"/> - ${entity.issue.audit.name}</h2>
                    </div>
                    <div class="ats-container-hd">
                        <h2><fmt:message key="issue.title"/> - ${entity.issue.name}</h2>
                    </div>
                    <div class="ats-container-bd">
                        <table class="nonDataTable">
                            <tr class="error-hidden"><td id="errors.listIndex">&#160;</td></tr>
                            <tr class="error-hidden"><td id="errors.name">&#160;</td></tr>
                        </table>
                        <table class="nonDataTable">
                            <tr>
                                <td class="ats-label"><fmt:message key="action.title"/></td>
                                <td>
                                    <table>
                                        <tr>
                                            <td class="width10">
                                                <input class="number" type="text" name="listIndex" value="${entity.listIndex}" onkeypress="return YAHOO.ats.inputFloat(event, this);"/>
                                            </td>
                                            <td class="width90">
                                                <input class="text" type="text" name="name" value="${entity.name}"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <table class="nonDataTable">
                            <tr>
                                <td class="width50 valign-top">
                                    <table>
                                        <tr>
                                            <td>
                                                <table class="fieldset">
                                                    <tr>
                                                        <td class="ats-label"><fmt:message key="action.issue.rating"/></td>
                                                        <td>
                                                            ${entity.issue.rating.name}
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table>
                                                    <tr class="error-hidden"><td id="errors.user">&#160;</td></tr>
                                                    <tr class="error-hidden"><td id="errors.actionGroupDivision">&#160;</td></tr>
                                                </table>
                                                <table class="fieldset">
                                                    <tr>
                                                        <c:set var="rowspan" value="${fn:length(entity.responsibleUsers) + 1}" />
                                                        <td rowspan="${rowspan}" class="ats-label"><fmt:message key="action.responsibleUser"/></td>
                                                        <td colspan="2">
                                                            <div id="filter0">
                                                                <input id="filter0Hidden" name="searchUserId" value="${searchUserId}" type="hidden"/>
                                                                <input id="filter0Input" class="filterInput" type="text" name="searchUserName" />&#160;
                                                                <div id="filter0Container" class="filterContainer"></div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <input type="button" id="addResponsibleUser" />
                                                        </td>
                                                    </tr>
                                                    <c:forEach items="${entity.responsibleUsers}" var="item" varStatus="status">
                                                        <c:set var="index" value="${status.index}" />
                                                        <tr>
                                                            <td colspan="2">
                                                                <input type="hidden" name="responsibleUsers2[${index}].id" value="${item.id}" />
                                                                <input type="text" class="text" id="responsibleUser.name" readonly="readonly" value="${item.name}" />
                                                            </td>
                                                            <td>
                                                                <input type="button" name="removeResponsibleUser" value="${item.id}" />
                                                            </td>
                                                        </tr>
                                                    </c:forEach>

                                                    <tr>
                                                        <c:set var="rowspan" value="${fn:length(entity.groupDivisions) + 1}" />
                                                        <td rowspan="${rowspan}" class="ats-label"><fmt:message key="action.groupDivisions"/></td>
                                                        <td class="width50">
                                                            <select id="groupId" class="select" name="groupId">
                                                                <option value=""><fmt:message key="option.select.one" /></option>
                                                                <c:forEach items="${groups}" var="item">
                                                                    <option value="${item.id}"><c:out value="${item.name}" /></option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td class="width50">
                                                            <select id="divisionId" class="select" name="divisionId">
                                                                <option value=""><fmt:message key="option.select.one" /></option>
                                                                <c:forEach items="${divisions}" var="item">
                                                                    <option value="${item.id}"><c:out value="${item.name}" /></option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td>
                                                            <input type="button" id="addGroupDivision" />
                                                        </td>
                                                    </tr>
                                                    <c:forEach items="${entity.groupDivisions}" var="item" varStatus="status">
                                                        <c:set var="index" value="${status.index}" />
                                                        <tr>
                                                            <td colspan="2">
                                                                <input type="hidden" name="groupDivisions2[${index}].id" value="${item.id}" />
                                                                <input type="text" class="text" id="groupDivision.name" readonly="readonly" value="${item.name}" />
                                                            </td>
                                                            <td>
                                                                <input type="button" name="removeGroupDivisionId" value="${item.id}" />
                                                            </td>
                                                        </tr>
                                                    </c:forEach>

                                                    <tr>
                                                        <td class="ats-label"><fmt:message key="action.borm.responsible"/></td>
                                                        <td colspan="3">
                                                            <ul>
                                                                <c:forEach items="${entity.bormResponsibleUsers}" var="item" >
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
                                <td class="width50 valign-top">
                                    <table>
                                        <tr>
                                            <td>
                                                <table>
                                                    <tr class="error-hidden"><td id="errors.dueDate">&#160;</td></tr>
                                                    <tr class="error-hidden"><td id="errors.businessStatus">&#160;</td></tr>
                                                    <tr class="error-hidden"><td id="errors.closeDate">&#160;</td></tr>
                                                </table>
                                                <table class="fieldset">
                                                    <tr>
                                                        <td class="ats-label"><fmt:message key="action.dueDate"/></td>
                                                        <td class="width33">
                                                            <fmt:formatDate var="dueDate" pattern="${datePattern}" value="${entity.dueDate}" />
                                                            <input id="f_date_dueDate" class="filterInput" type="text" name="dueDate" value="${dueDate}" readonly="readonly" style="width: 99%"/>
                                                        </td>
                                                        <td>
                                                            <input type="button" id="resetDueDate" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="ats-label"><fmt:message key="action.businessStatus"/></td>
                                                        <td colspan="2">
                                                            <input type="hidden" id="actionStatusId" name="businessStatus.actionStatus.actionStatusId" value="${entity.businessStatus.actionStatus.id}"/>
                                                            <select id="businessStatusId" class="select" name="businessStatus.businessStatusId">
                                                                <option value=""><fmt:message key="option.select.one" /></option>
                                                                <c:forEach items="${businessStatuses}" var="item">
                                                                    <c:choose>
                                                                        <c:when test="${item.id eq entity.businessStatus.id}">
                                                                            <option value="${item.id}" selected="selected"><c:out value="${item.name}" /></option>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <option value="${item.id}"><c:out value="${item.name}" /></option>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="ats-label"><fmt:message key="action.business.date.closed"/></td>
                                                        <td class="width33">
                                                            <fmt:formatDate var="closedDate" pattern="${datePattern}" value="${entity.closedDate}" />
                                                            <input id="f_date_closedDate" class="filterInput" type="text" name="closedDate" value="${closedDate}" readonly="readonly" style="width: 99%"/>
                                                        </td>
                                                        <td>
                                                            <input type="button" id="resetClosedDate" />
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table class="fieldset">
                                                    <tr>
                                                        <td class="ats-label"><fmt:message key="action.followup.status"/></td>
                                                        <td colspan="2">
                                                            <select id="actionFollowupStatusId" class="select" name="followupStatus.actionFollowupStatusId">
                                                                <option value=""><fmt:message key="option.select.one" /></option>
                                                                <c:forEach items="${followupStatuses}" var="item">
                                                                    <c:choose>
                                                                        <c:when test="${item.id eq entity.followupStatus.id}">
                                                                            <option value="${item.id}" selected="selected"><c:out value="${item.name}" /></option>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <option value="${item.id}"><c:out value="${item.name}" /></option>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="ats-label"><fmt:message key="action.followup.date"/></td>
                                                        <td class="width33">
                                                            <fmt:formatDate var="followupDate" pattern="${datePattern}" value="${entity.followupDate}" />
                                                            <input id="f_date_followupDate" class="filterInput" type="text" name="followupDate" value="${followupDate}" readonly="readonly" style="width: 99%"/>
                                                        </td>
                                                        <td>
                                                            <input type="button" id="resetFollowupDate" />
                                                        </td>
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
                    <table>
                        <tr class="error-hidden"><td id="errors.detail">&#160;</td></tr>
                    </table>
                    <textarea class="textarea" name="detail">${entity.detail}</textarea>
                </ats:window>

                <fmt:message var="title" key="action.comments" />
                <ats:window title="${title}">
                    <div>
                        <c:set var="add"><fmt:message key="comment.add" /></c:set>
                        <input type="button" id="addComment" value="${add}" />
                    </div>
                    <div id="dataDiv">&#160;</div>
                </ats:window>

                <fmt:message var="title" key="action.auditLogs" />
                <ats:window title="${title}" collapse="true" collapsed="true">
                    <div id="dataAuditLogDiv">&#160;</div>
                </ats:window>

                <div id="toolbarDiv2">&#160;</div>
            </form>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/auditAdmin/issue/action/edit.js" />
    </div>

</jsp:root>