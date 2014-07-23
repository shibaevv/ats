<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder">
        <fmt:message var="title" key="menu.action" />
        <ats:window id="dataHeader" title="${title}" collapse="true">
            <form id="dataForm" name="dataForm">
                <input id="pageSize" name="pageSize" value="${pageSize}" type="hidden" />
                <input id="startIndex" name="startIndex" value="${startIndex}" type="hidden" />
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />

                <table class="nonDataTable">
                    <tr>
                        <td class="width40" rowspan="4">
                            <table>
                                <tr>
                                    <th><fmt:message key="action.availableGroupDivisions"/></th>
                                    <th>&#160;</th>
                                    <th><fmt:message key="action.selectedGroupDivisions"/></th>
                                </tr>
                                <tr>
                                    <td class="width50">
                                        <div id="availableGroupDivisions" class="selectionPanel">
                                            <c:forEach items="${availableGroupDivisions}" var="item">
                                                <div id="availableGroupDivisions${item.id}" onclick="YAHOO.ats.toggleClass(this,'selectionActive');">
                                                    <input type="hidden" name="availableGroupDivisions" value="${item.group.id},${item.division.id}" />${item.name}
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </td>
                                    <td class="width20em center">
                                        <fmt:message var="move0" key="button.add" />
                                        <input id="move0" type="button" value="${move0}" />
                                        <fmt:message var="move1" key="button.addAll" />
                                        <input id="move1" type="button" value="${move1}" />
                                        <fmt:message var="move2" key="button.remove" />
                                        <input id="move2" type="button" value="${move2}" />
                                        <fmt:message var="move3" key="button.removeAll" />
                                        <input id="move3" type="button" value="${move3}" />
                                    </td>
                                    <td class="width50">
                                        <div id="actionGroupDivisions" class="selectionPanel">
                                            <c:forEach items="${actionGroupDivisions}" var="item">
                                                <div id="actionGroupDivisions${item.id}" onclick="YAHOO.ats.toggleClass(this,'selectionActive');">
                                                    <input type="hidden" name="actionGroupDivisions" value="${item.group.id},${item.division.id}" />${item.name}
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="filterLabel"><fmt:message key="audit.title"/></td>
                        <td class="width40" colspan="3">
                            <table>
                                <tr>
                                    <td>
                                        <div id="filter0">
                                            <input id="filter0Hidden" type="hidden" name="searchAuditId" value="${searchAuditId}" />
                                            <input id="filter0Input" class="filterInput" type="text" name="searchAuditName" value="${searchAuditName}" />&#160;
                                            <div id="filter0Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <!--td class="width3em yui-ac">
                                        <span id="filter0Toggle" class="filterToggle">&#160;</span>
                                    </td-->
                                </tr>
                            </table>
                        </td>
                        <td class="filterLabel"><fmt:message key="action.actionStatus"/></td>
                        <td class="width20">
                            <table>
                                <tr>
                                    <td>
                                        <div id="filter1">
                                            <c:choose>
                                                <c:when test="${actionStatusId eq 1}">
                                                    <input id="openActions" type="radio" class="radio" name="actionStatusId" value="1" title="Show Outstanding Actions" checked="checked">Open</input>&#160;
                                                    <input id="closedActions" type="radio" class="radio" name="actionStatusId" value="2" title="Show Closed Actions">Closed</input>&#160;
                                                    <input id="allActions" type="radio" class="radio" name="actionStatusId" value="" title="Show All Actions">All</input>&#160;
                                                </c:when>
                                                <c:when test="${actionStatusId eq 2}">
                                                    <input id="openActions" type="radio" class="radio" name="actionStatusId" value="1" title="Show Outstanding Actions">Open</input>&#160;
                                                    <input id="closedActions" type="radio" class="radio" name="actionStatusId" value="2" title="Show Closed Actions" checked="checked">Closed</input>&#160;
                                                    <input id="allActions" type="radio" class="radio" name="actionStatusId" value="" title="Show All Actions">All</input>&#160;
                                                </c:when>
                                                <c:otherwise>
                                                    <input id="openActions" type="radio" class="radio" name="actionStatusId" value="1" title="Show Outstanding Actions">Open</input>&#160;
                                                    <input id="closedActions" type="radio" class="radio" name="actionStatusId" value="2" title="Show Closed Actions">Closed</input>&#160;
                                                    <input id="allActions" type="radio" class="radio" name="actionStatusId" value="" title="Show All Actions" checked="checked">All</input>&#160;
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </td>
                                    <!--td>
                                        <div id="filter1">
                                            <input id="filter1Hidden" type="hidden" name="actionStatusId" value="${actionStatusId}"/>
                                            <input id="filter1Input" class="filterInput" type="text" name="actionStatusName" value="${actionStatusName}" />&#160;
                                            <div id="filter1Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <td class="width3em yui-ac">
                                        <span id="filter1Toggle" class="filterToggle">&#160;</span>
                                    </td-->
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="filterLabel"><fmt:message key="action.responsibleUser"/></td>
                        <td colspan="3">
                            <table>
                                <tr>
                                    <td>
                                        <div id="filter2">
                                            <input id="filter2Hidden" type="hidden" name="responsibleUser.id" value="${searchUserId}" />
                                            <input id="filter2Input" class="filterInput" type="text" name="searchUserName" value="${searchUserName}" />&#160;
                                            <div id="filter2Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <!--td class="width3em yui-ac">
                                        <span id="filter2Toggle" class="filterToggle">&#160;</span>
                                    </td-->
                                </tr>
                            </table>
                        </td>
                        <td class="filterLabel"><fmt:message key="action.businessStatus"/></td>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <div id="filter3">
                                            <input id="filter3Hidden" type="hidden" name="businessStatusId" value="${businessStatusId}"/>
                                            <input id="filter3Input" class="filterInput" type="text" name="businessStatusName" value="${businessStatusName}" />&#160;
                                            <div id="filter3Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <td class="width3em yui-ac">
                                        <span id="filter3Toggle" class="filterToggle">&#160;</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="filterLabel"><fmt:message key="action.dueDate.from"/></td>
                        <td class="width20">
                            <table>
                                <tr>
                                    <td class="width80">
                                        <div id="filter4">
                                            <input id="f_date_dueDateFrom" class="filterInput" type="text" name="dueDateFrom" value="${dueDateFrom}" title="on or after date" readonly="readonly"/>
                                        </div>
                                    </td>
                                    <td class="width20">
                                        <input type="button" id="resetDueDateFrom" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="filterLabel"><fmt:message key="action.dueDate.to"/></td>
                        <td class="width20">
                            <table>
                                <tr>
                                    <td class="width80">
                                        <div id="filter5">
                                            <input id="f_date_dueDateTo" class="filterInput" type="text" name="dueDateTo" value="${dueDateTo}" title="on or before date" readonly="readonly"/>
                                        </div>
                                    </td>
                                    <td class="width20">
                                        <input type="button" id="resetDueDateTo" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="filterLabel"><fmt:message key="main.actions.priority"/></td>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <div id="filter6">
                                            <input id="filter4Hidden" type="hidden" name="ratingId" value="${ratingId}"/>
                                            <input id="filter4Input" class="filterInput" type="text" name="ratingName" value="${ratingName}" />&#160;
                                            <div id="filter4Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <td class="width3em yui-ac">
                                        <span id="filter4Toggle" class="filterToggle">&#160;</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="filterLabel"><fmt:message key="action.closedDate.from"/></td>
                        <td>
                            <table>
                                <tr>
                                    <td class="width80">
                                        <div id="filter7">
                                            <input id="f_date_closedDateFrom" class="filterInput" type="text" name="closedDateFrom" value="${closedDateFrom}" title="on or after date" readonly="readonly"/>
                                        </div>
                                    </td>
                                    <td class="width20">
                                        <input type="button" id="resetClosedDateFrom" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="filterLabel"><fmt:message key="action.closedDate.to"/></td>
                        <td>
                            <table>
                                <tr>
                                    <td class="width80">
                                        <div id="filter8">
                                            <input id="f_date_closedDateTo" class="filterInput" type="text" name="closedDateTo" value="${closedDateTo}" title="on or before date" readonly="readonly"/>
                                        </div>
                                    </td>
                                    <td class="width20">
                                        <input type="button" id="resetClosedDateTo" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="filterLabel"><fmt:message key="reportType.name"/></td>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <div id="filter9">
                                            <input id="filter5Hidden" type="hidden" name="reportTypeId" value="${reportTypeId}"/>
                                            <input id="filter5Input" class="filterInput" type="text" name="reportTypeName" value="${reportTypeName}" />&#160;
                                            <div id="filter5Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <td class="width3em yui-ac">
                                        <span id="filter5Toggle" class="filterToggle">&#160;</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <!--tr>
                        <td colspan="7">
                            <table>
                                <tr>
                                    <td class="filterLabel">Search..</td>
                                    <td class="width100">
                                        <input class="filterInput" type="text" name="compassQuery" title="eg closedDate:02-Mar-2009 AND dueDate:*-2009" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr-->
                    <tr>
                        <td class="ats-h2">
                            <a id="actionHelp" href="${actionHelpUrl}" target="_blank"><fmt:message key="action.help" /></a>
                        </td>
                        <td colspan="6">
                            <div id="toolbarDiv">&#160;</div>
                        </td>
                    </tr>
                </table>
            </form>
        </ats:window>
        <div id="dataCenter" class="ats-container">
            <div id="dataDiv">&#160;</div>
        </div>
        <div id="dataFooter" class="ats-container">
            <div id="paginatorDiv">&#160;</div>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/audit/issue/action/main.js" />
    </div>

</jsp:root>