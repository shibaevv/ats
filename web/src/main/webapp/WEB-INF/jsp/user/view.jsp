<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder">
        <fmt:message var="title" key="menu.user" />
        <ats:window id="dataHeader" title="${title}">
            <form id="dataForm" name="dataForm">
                <table class="nonDataTable">
                    <tr>
                        <td class="filterLabel"><fmt:message key="user.find.name"/></td>
                        <td class="width50">
                            <table>
                                <tr>
                                    <td>
                                        <div id="filter0">
                                            <input id="filter0Hidden" name="searchUserId" value="${searchUserId}" type="hidden" />
                                            <input id="filter0Input" class="filterInput" type="text" name="searchUserName" value="${searchUserName}" onKeyPress="return disableEnterKey(event)"/>&#160;
                                            <div id="filter0Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <!--td class="width3em yui-ac">
                                        <span id="filter0Toggle" class="filterToggle">&#160;</span>
                                    </td-->
                                </tr>
                            </table>
                        </td>
                        <td class="width33">
                            <!-- div id="toolbarDiv">&#160;</div-->
                        </td>
                    </tr>
                </table>
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />
            </form>
        </ats:window>

        <fmt:message var="title" key="user.details" />
        <ats:window title="${title}">
            <table class="nonDataTable">
                <tr>
                    <td class="ats-label"><fmt:message key="user.name"/></td>
                    <td>${entity.fullName}</td>
                </tr>
                <tr>
                    <td class="ats-label"><fmt:message key="user.groupDivisions"/></td>
                    <td class="left">
                        <ul>
                            <c:forEach items="${entity.groupDivisions}" var="item" varStatus="status">
                                <li>${item.name}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </table>
            <table>
                <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
                <tr class="error-hidden"><td id="errors.user">&#160;</td></tr>
                <tr class="error-hidden"><td id="errors.userMatrix">&#160;</td></tr>
                <tr class="error-hidden"><td id="errors.roleId">&#160;</td></tr>
                <tr class="error-hidden"><td id="errors.groupId">&#160;</td></tr>
                <tr class="error-hidden"><td id="errors.divisionId">&#160;</td></tr>
                <tr class="error-hidden"><td id="errors.reportTypeId">&#160;</td></tr>
            </table>
        </ats:window>

        <fmt:message var="title" key="userMatrix" />
        <ats:window id="dataCenter" title="${title}">
            <div>
                 <c:set var="add"><fmt:message key="userMatrix.add" /></c:set>
                 <input type="button" id="addUserMatrix" value="${add}" />
            </div>
            <div id="dataDiv">&#160;</div>
        </ats:window>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/user/view.js" />
    </div>

</jsp:root>