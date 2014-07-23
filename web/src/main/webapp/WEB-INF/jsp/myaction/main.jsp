<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder">
        <fmt:message var="title" key="menu.myaction" />
        <ats:window id="dataHeader" title="${title}">
            <form id="myactionForm" name="myactionForm">
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />
                <input id="actionStatusId" name="actionStatusId" value="${actionStatusId}" type="hidden" />

                <table class="nonDataTable">
                    <tr class="ats-h1">
                        <td class="width10em"><fmt:message key="action.actionStatus"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${actionStatusId eq 1}">
                                    <input id="openActions" type="radio" class="radio" name="_actionStatusId" value="1" title="Show Outstanding Actions" checked="checked">Open</input>&#160;
                                    <input id="closedActions" type="radio" class="radio" name="_actionStatusId" value="2" title="Show Closed Actions">Closed</input>&#160;
                                    <input id="allActions" type="radio" class="radio" name="_actionStatusId" value="" title="Show All Actions">All</input>&#160;
                                </c:when>
                                <c:when test="${actionStatusId eq 2}">
                                    <input id="openActions" type="radio" class="radio" name="_actionStatusId" value="1" title="Show Outstanding Actions">Open</input>&#160;
                                    <input id="closedActions" type="radio" class="radio" name="_actionStatusId" value="2" title="Show Closed Actions" checked="checked">Closed</input>&#160;
                                    <input id="allActions" type="radio" class="radio" name="_actionStatusId" value="" title="Show All Actions">All</input>&#160;
                                </c:when>
                                <c:otherwise>
                                    <input id="openActions" type="radio" class="radio" name="_actionStatusId" value="1" title="Show Outstanding Actions">Open</input>&#160;
                                    <input id="closedActions" type="radio" class="radio" name="_actionStatusId" value="2" title="Show Closed Actions">Closed</input>&#160;
                                    <input id="allActions" type="radio" class="radio" name="_actionStatusId" value="" title="Show All Actions" checked="checked">All</input>&#160;
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </form>
        </ats:window>
        <div id="dataCenter" class="ats-container">
            <div id="dataDiv">&#160;</div>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/myaction/main.js" />
    </div>

</jsp:root>