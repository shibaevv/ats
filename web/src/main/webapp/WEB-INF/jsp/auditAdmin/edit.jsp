<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder" class="auditAdmin">
        <div id="dataCenter" class="ats-container">
            <form id="auditForm" name="auditForm"
                  enctype="multipart/form-data"
                  action="auditAdmin/edit.do?readOnly=false">

                <input type="hidden" id="auditId" name="auditId" value="${entity.auditId}" />

                <div id="toolbarDiv">&#160;</div>
                <span></span>

                <table>
                    <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
                    <tr class="error-hidden"><td id="errors">&#160;</td></tr>
                </table>
                <div id="reportData">
                    <fmt:message var="title" key="audit.details" />
                    <ats:window title="${title}">
                         <table class="nonDataTable">
                             <tr class="error-hidden"><td id="errors.name">&#160;</td></tr>
                             <tr class="error-hidden"><td id="errors.reportType">&#160;</td></tr>
                             <tr class="error-hidden"><td id="errors.issuedDate">&#160;</td></tr>
                             <tr class="error-hidden"><td id="errors.groupDivision">&#160;</td></tr>
                             <tr class="error-hidden"><td id="errors.document">&#160;</td></tr>
                             <tr class="error-hidden"><td id="errors.duplicateGroupDivision">&#160;</td></tr>
                         </table>
                         <table class="nonDataTable">
                             <tr>
                                 <td class="ats-label"><fmt:message key="audit.admin.audit.name"/></td>
                                 <td colspan="4">
                                     <input class="text" type="text" name="name" value="${entity.name}" />
                                 </td>
                             </tr>
                             <tr>
                                 <td class="ats-label"><fmt:message key="audit.admin.audit.type"/></td>
                                 <td class="width40">
                                     <select class="select" name="reportType.reportTypeId">
                                         <option value=""><fmt:message key="option.select.one" /></option>
                                         <c:forEach items="${reportTypes}" var="item">
                                             <c:choose>
                                                 <c:when test="${item.id eq entity.reportType.id}">
                                                     <option value="${item.id}" selected="selected"><c:out value="${item.name}" /></option>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <option value="${item.id}"><c:out value="${item.name}" /></option>
                                                 </c:otherwise>
                                             </c:choose>
                                         </c:forEach>
                                     </select>
                                 </td>
                                 <td class="ats-label"><fmt:message key="audit.admin.issue.date"/></td>
                                 <td class="width33">
                                     <fmt:formatDate var="issuedDate" pattern="${datePattern}" value="${entity.issuedDate}" />
                                     <input id="f_date_issuedDate" type="text" class="text" name="issuedDate" value="${issuedDate}" title="Issued Date" readonly="readonly"/>
                                 </td>
                                 <td>
                                     <input type="button" id="resetIssuedDate" />
                                 </td>
                             </tr>
                             <tr>
                                 <td class="ats-label"><fmt:message key="audit.groupDivisions" /></td>
                                 <td class="width40">
                                     <table>
                                         <tr>
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
                                                 <c:set var="add"><fmt:message key="audit.admin.add.gr.div" /></c:set>
                                                 <input type="button" id="addGroupDivision" value="${add}" />
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
                                                 <input type="button" name="removeGroupDivision" value="${item.id}" />
                                             </td>
                                         </tr>
                                         </c:forEach>
                                     </table>
                                 </td>
                                 <td class="ats-label"><fmt:message key="audit.admin.attachment"/></td>
                                 <td colspan="2">
                                     <table>
                                         <tr>
                                             <td>
                                                 <input type="file" id="attachment" name="attachment" />
                                             </td>
                                             <td>
                                                 <c:set var="add"><fmt:message key="audit.document.add" /></c:set>
                                                 <input type="button" id="addAttachment" value="${add}" />
                                             </td>
                                         </tr>
                                         <tr>
                                             <td>
                                                 <input type="hidden" name="document.documentId" value="${entity.document.documentId}" />
                                                 <input type="text" class="text" id="document.name" readonly="readonly" value="${entity.document.name}" />
                                                 <fmt:formatDate pattern="${dateTimePattern}" value="${entity.document.createdDate}"  />
                                             </td>
                                             <td>
                                                 <fmt:message var="remove" key="audit.document.remove" />
                                                 <input type="button" id="removeAttachment" title="${remove}" />
                                             </td>
                                         </tr>
                                     </table>
                                 </td>
                             </tr>
                         </table>
                    </ats:window>
                </div>
                <div id="issueData">
                    <c:forEach items="${entity.issues}" var="issue" varStatus="status">
                        <c:set var="index" value="${status.index}" />
                        <c:set var="title"><fmt:message key="audit.issues" /> : ${issue.listIndex} - ${issue.name}</c:set>
                        <input type="hidden" id="issue.${issue.issueId}" name="issues2[${index}].issueId" value="${issue.issueId}" />
                        <ats:window id="issueRow.${issue.id}" title="${title}" collapse="true">
                            <table class="nonDataTable">
                                <tr class="error-hidden"><td id="errors.issue.${index}.listIndex">&#160;</td></tr>
                                <tr class="error-hidden"><td id="errors.issue.${index}.name">&#160;</td></tr>
                                <tr class="error-hidden"><td id="errors.issue.${index}.risk">&#160;</td></tr>
                                <tr class="error-hidden"><td id="errors.issue.${index}.rating">&#160;</td></tr>
                                <tr class="error-hidden"><td id="errors.issue.${index}.detail">&#160;</td></tr>
                                <tr class="error-hidden"><td id="errors.issue.${index}.action">&#160;</td></tr>
                            <table class="nonDataTable">
                            </table>
                                <tr>
                                    <td>
                                        <input type="button" name="deleteIssue" value="${issue.issueId}" />
                                    </td>
                                </tr>
                            </table>
                            <table class="nonDataTable">
                                <tr>
                                    <td class="ats-label"><fmt:message key="audit.admin.issue.title"/></td>
                                    <td colspan="3">
                                        <table>
                                            <tr>
                                                <td class="width10">
                                                    <input id="issuesListIndex.${index}" class="number" type="text" name="issues2[${index}].listIndex" value="${issue.listIndex}" onkeypress="return YAHOO.ats.inputInteger(event, this);"/>
                                                </td>
                                                <td class="width90">
                                                    <input class="text" type="text" name="issues2[${index}].name" value="${issue.name}" />
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ats-label"><fmt:message key="audit.admin.parent.risk"/></td>
                                    <td class="width33">
                                        <select id="risk.${index}" class="select" name="issues2[${index}].risk.parentRiskId">
                                            <option value=""><fmt:message key="option.select.one" /></option>
                                            <c:forEach items="${parentRisks}" var="item">
                                                <c:choose>
                                                    <c:when test="${item.id eq issue.risk.id}">
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
                                    <td class="ats-label"><fmt:message key="audit.admin.parent.risk.category"/></td>
                                    <td>
                                        <input id="category.${index}" class="text" type="text" value="${issue.risk.category.name}" readonly="readonly"/>
                                    </td>
                                <tr>
                                </tr>
                                <tr>
                                    <td class="ats-label"><fmt:message key="audit.admin.rating"/></td>
                                    <td class="width33">
                                        <select id="ratingId" class="select" name="issues2[${index}].rating.ratingId">
                                            <option value=""><fmt:message key="option.select.one" /></option>
                                            <c:forEach items="${ratings}" var="item">
                                                <c:choose>
                                                    <c:when test="${item.id eq issue.rating.id}">
                                                        <option value="${item.id}" selected="selected"><c:out value="${item.name}" /></option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${item.id}"><c:out value="${item.name}" /></option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td colspan="2"/>
                                </tr>
                                <tr>
                                    <td class="ats-label"><fmt:message key="audit.admin.issue.findings"/></td>
                                    <td colspan="3">
                                        <textarea class="textarea" name="issues2[${index}].detail">${issue.detail}</textarea>
                                    </td>
                                </tr>
                            </table>
                            <div id="actionData">
                                <c:if test="${not empty issue.actions}">
                                <table class="dataTable">
                                    <caption><fmt:message key="audit.admin.action.items"/></caption>
                                     <tr>
                                         <th width="5%"><fmt:message key="audit.admin.action.colomn.no"/></th>
                                         <th width="*"><fmt:message key="audit.admin.action.colomn.agreedAction"/></th>
                                         <th width="10%"><fmt:message key="audit.admin.action.colomn.dueDate"/></th>
                                         <th width="15%"><fmt:message key="audit.admin.action.colomn.businessStatus"/></th>
                                         <th width="15%"><fmt:message key="audit.admin.action.colomn.personResponsible"/></th>
                                         <th width="15%"><fmt:message key="audit.admin.action.colomn.grp.div"/></th>
                                         <th width="5%" colspan="2"><fmt:message key="audit.admin.action.colomn.edit.delete"/></th>
                                     </tr>
                                     <c:forEach items="${issue.actions}" var="action" varStatus="status" >
                                         <tr id="actionRow.${action.id}" class="${status.index % 2 eq 0 ? 'even' : 'odd'}">
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
                                                  <input type="button" name="editAction" value="${action.actionId}" />
                                              </td>
                                              <td>
                                                  <input type="button" name="deleteAction" value="${action.actionId}" />
                                              </td>
                                         </tr>
                                     </c:forEach>
                                </table>
                                </c:if>
                                <table class="nonDataTable">
                                   <tr>
                                      <td>
                                         <input type="button" name="addAction" value="${issue.issueId}" />
                                      </td>
                                   </tr>
                                </table>
                            </div>
                        </ats:window>
                    </c:forEach>
                </div>

                <div id="toolbarDiv2">&#160;</div>
            </form>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/auditAdmin/edit.js" />
    </div>

</jsp:root>