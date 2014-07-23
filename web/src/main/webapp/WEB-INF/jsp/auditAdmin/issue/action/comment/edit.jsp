<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <form id="commentForm" name="dataForm"
          enctype="multipart/form-data"
          action="commentAdmin/edit.do?readOnly=false">

        <input type="hidden" name="redirect" value="${redirect}" />
        <input type="hidden" name="commentId" value="${entity.id}" />
        <input type="hidden" name="actionId" value="${entity.action.id}" />

        <table class="nonDataTable">
            <tr>
                <td class="ats-h1"><fmt:message key="audit.title" /></td>
                <td class="left">${entity.action.issue.audit.name}</td>
            </tr>
            <tr>
                <td class="ats-h2"><fmt:message key="comment.issue.title" /></td>
                <td class="left">${entity.action.issue.name}</td>
            </tr>
            <tr>
                <td class="ats-h3"><fmt:message key="comment.action.title" /></td>
                <td class="left">${entity.action.name}</td>
            </tr>
            <tr>
                <td class="ats-h3"><fmt:message key="comment.current.business.status" /></td>
                <td class="left">${entity.action.businessStatus.name}</td>
            </tr>
        </table>

        <table class="width100">
            <tr class="error-hidden"><td id="errors">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
        </table>
        <fmt:message var="title" key="action.businessStatus.change" />
        <ats:window title="${title}">
            <table>
                <tr class="error-hidden"><td id="errors.businessStatus">&#160;</td></tr>
            </table>
            <table class="nonDataTable">
                <tr>
                    <td class="ats-label nowrap"><fmt:message key="comment.by" /></td>
                    <td class="left">
                        ${entity.createdBy.fullName}
                    </td>
                    <td class="ats-label nowrap"><fmt:message key="comment.date" /></td>
                    <td class="left">
                        <fmt:formatDate pattern="${datePattern}" value="${entity.createdDate}" />
                    </td>
                </tr>
                <c:if test="${businessStatusEditable}">
                    <tr>
                        <td class="ats-label nowrap"><fmt:message key="comment.update.business.status.to" /></td>
                        <td>
                            <select id="businessStatusId" class="select" name="businessStatus.businessStatusId">
                                <option value=""><fmt:message key="option.select.one" /></option>
                                <c:forEach items="${businessStatuses}" var="item">
                                    <c:choose>
                                        <c:when test="${item.id eq entity.action.businessStatus.id}">
                                            <option value="${item.id}" selected="selected"><c:out value="${item.name}" /></option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${item.id}"><c:out value="${item.name}" /></option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                        <td colspan="2" />
                    </tr>
                </c:if>
            </table>
        </ats:window>

        <fmt:message var="title" key="comment.attachment" />
        <ats:window title="${title}">
            <table id="attachmentTable" class="nonDataTable">
                <tr>
                    <th colspan="2">
                        <a id="addAttachment" href="javascript:;">
                            <span class="add">&#160;</span>
                        </a>
                        <fmt:message key="comment.attachment.add" />
                    </th>
                </tr>
                <tr id="attachmentRow2clone" style="display: none;">
                    <td class="left">
                        <input type="file" size="70" disabled="disabled"/>
                    </td>
                    <td>
                        <a title="Remove Attachment" href="javascript:removeAttachment();">
                            <span class="delete">&#160;</span>
                        </a>
                    </td>
                </tr>
            </table>
        </ats:window>

        <table>
            <tr class="error-hidden"><td id="errors.detail">&#160;</td></tr>
        </table>
        <fmt:message var="title" key="comment.detail" />
        <ats:window title="${title}">
            <textarea class="textarea" name="detail">${entity.detail}</textarea>
        </ats:window>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/auditAdmin/issue/action/comment/edit.js" />
    </form>

</jsp:root>