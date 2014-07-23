<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">

    <form name="userMatrix"
          action="user/matrix/edit.do?dispatch=save&amp;readOnly=false">

        <input type="hidden" id="userMatrixId" name="userMatrixId" value="${entity.userMatrixId}" />
        <input type="hidden" id="userId" name="user.userId" value="${entity.user.userId}" />

        <table>
            <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.userMatrix">&#160;</td></tr>
        </table>
        <table class="nonDataTable">
            <tr class="error-hidden">
                <td />
                <td id="errors.groupId">&#160;</td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="group.name" /></td>
                <td>
                    <select id="groupId" class="select" name="group.groupId">
                        <option value=""><fmt:message key="option.select.one" /></option>
                        <c:forEach items="${groups}" var="item">
                            <c:choose>
                                <c:when test="${not empty item.id and item.id eq entity.group.id}">
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
            <tr class="error-hidden">
                <td />
                <td id="errors.divisionId">&#160;</td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="division.name" /></td>
                <td>
                    <select id="divisionId" class="select" name="division.divisionId">
                        <option value=""><fmt:message key="option.select.one" /></option>
                        <c:forEach items="${divisions}" var="item">
                            <c:choose>
                                <c:when test="${item.id eq entity.division.id}">
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
            <tr class="error-hidden">
                <td />
                <td id="errors.roleId">&#160;</td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="role.name" /></td>
                <td>
                    <select class="select" name="role.roleId">
                        <option value=""><fmt:message key="option.select.one" /></option>
                        <c:forEach items="${roles}" var="item">
                            <c:choose>
                                <c:when test="${item.id eq entity.role.id}">
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
            <tr class="error-hidden">
                <td />
                <td id="errors.reportTypeId">&#160;</td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="reportType.name" /></td>
                <td>
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
            </tr>
        </table>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/user/matrix/edit.js" />
    </form>

</jsp:root>