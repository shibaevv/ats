<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder" class="auditAdmin">
        <div id="dataCenter" class="ats-container">
            <form id="dataForm" name="dataForm">
                <input id="auditId" value="${entity.auditId}" type="hidden" />
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />

                <div id="toolbarDiv">&#160;</div>
                <span></span>

                <div id="reportData">
                    <fmt:message var="title" key="audit.admin.details" />
                    <ats:window title="${title}">
                    <c:if test="${entity.published}">
                        <fmt:formatDate var="reportPublishedDate" value="${entity.publishedDate}" pattern="${datePattern}" />
                        <ats:winoption url="javascript:;" title="${entity.publishedBy.fullName} (${reportPublishedDate})">Published</ats:winoption>
                    </c:if>
                        <table class="nonDataTable">
                            <tr>
                                <td class="ats-label"><fmt:message key="audit.admin.title"/></td>
                                <td colspan="3">${entity.name}</td>
                            </tr>
                            <tr>
                                <td class="ats-label"><fmt:message key="audit.admin.audit.type"/></td>
                                <td>${entity.reportType.name}</td>
                                <td class="ats-label"><fmt:message key="audit.admin.attachment"/></td>
                                <td>
                                    <c:if test="${not empty entity.document.id}">
                                       <a href="javascript:YAHOO.ats.downloadReport(${entity.id},${entity.document.id});" title="Download ${entity.document.name}">
                                           <img src="css/img/ext/${entity.document.ext}.gif" />&#160;${entity.document.name}
                                       </a>
                                       (<fmt:formatDate pattern="${dateTimePattern}" value="${entity.document.createdDate}"  />)
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="ats-label"><fmt:message key="audit.admin.groupDivisions"/></td>
                                <td class="left">
                                    <ul>
                                        <c:forEach items="${entity.groupDivisions}" var="item" varStatus="status">
                                            <li>${item.name}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                                <td class="ats-label"><fmt:message key="audit.admin.issue.date"/></td>
                                <td>
                                    <c:set var="issuedDate"><fmt:formatDate pattern="${datePattern}" value="${entity.issuedDate}" /></c:set>${issuedDate}
                                </td>
                            </tr>
                        </table>
                    </ats:window>
                </div>
                
                <div id="issueData">
                    <c:forEach items="${entity.issues}" var="issue" varStatus="status" >
                        <c:set var="index" value="${status.index}" />
                        <c:set var="title"><fmt:message key="audit.admin.issues" /> : ${issue.listIndex} - ${issue.name}</c:set>
                        <ats:window title="${title}" collapse="true">
                            <c:if test="${issue.published}">
                                <fmt:formatDate var="issuePublishedDate" value="${issue.publishedDate}" pattern="${datePattern}" />
                                <ats:winoption url="javascript:;" title="${issue.publishedBy.fullName} (${issuePublishedDate})">Published</ats:winoption>
                            </c:if>
                            <table class="nonDataTable">
                                <tr>
                                    <td class="ats-label nowrap"><fmt:message key="audit.admin.issue.title"/></td>
                                    <td>${issue.name}</td>
                                </tr>
                                <tr>
                                    <td class="ats-label nowrap"><fmt:message key="audit.admin.parent.risk"/></td>
                                    <td>${issue.risk.name}</td>
                                </tr>
                                <tr>
                                    <td class="ats-label nowrap"><fmt:message key="audit.admin.parent.risk.category"/></td>
                                    <td>${issue.risk.category.name}</td>
                                </tr>
                                <tr>
                                    <td class="ats-label nowrap"><fmt:message key="audit.admin.rating"/></td>
                                    <td>${issue.rating.name}</td>
                                </tr>
                                <tr>
                                    <td class="ats-label nowrap"><fmt:message key="audit.admin.issue.findings"/></td>
                                    <td><ats:text text="${issue.detail}" /></td>
                                </tr>
                            </table>
                            <c:if test="${not empty issue.actions}">
                                <div id="actionDiv">
                                    <table class="dataTable">
                                    <caption><fmt:message key="audit.admin.action.items"/></caption>
                                        <tr>
                                            <th width="5%"><fmt:message key="audit.admin.action.colomn.no"/></th>
                                            <th width="*"><fmt:message key="audit.admin.action.colomn.agreedAction"/></th>
                                            <th width="10%"><fmt:message key="audit.admin.action.colomn.dueDate"/></th>
                                            <th width="15%"><fmt:message key="audit.admin.action.colomn.businessStatus"/></th>
                                            <th width="15%"><fmt:message key="audit.admin.action.colomn.personResponsible"/></th>
                                            <th width="15%"><fmt:message key="audit.admin.action.colomn.grp.div"/></th>
                                            <th width="10%">Published</th>
                                        </tr>
                                        <c:forEach items="${issue.actions}" var="action" varStatus="status" >
                                            <tr class="${status.index % 2 eq 0 ? 'even' : 'odd'}">
                                                <td>${action.listIndex}</td>
                                                <td><ats:crop text="${action.detail}"/></td>
                                                <td><fmt:formatDate pattern="${datePattern}" value="${action.dueDate}" /></td>
                                                <td>${action.businessStatus.name}</td>
                                                <td>${action.firstResponsibleUser.name}</td>
                                                <td>
                                                    <ul>
                                                        <c:forEach items="${action.groupDivisions}" var="item" varStatus="status">
                                                            <li>${item.name}</li>
                                                        </c:forEach>
                                                    </ul>
                                                </td>
                                                <td>
                                                    <c:if test="${action.published}">
                                                        <fmt:formatDate var="actionPublishedDate" value="${action.publishedDate}" pattern="${datePattern}" />
                                                        <a href="javascript:;" title="${action.publishedBy.fullName} (${actionPublishedDate})" style="text-decoration: none; color: blue;">Published</a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </c:if>
                        </ats:window>
                    </c:forEach>
                </div>
            </form>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/auditAdmin/view.js" />
    </div>

</jsp:root>