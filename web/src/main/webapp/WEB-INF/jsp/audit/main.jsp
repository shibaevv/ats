<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder">
        <fmt:message var="title" key="menu.report" />
        <ats:window id="dataHeader" title="${title}" collapse="true">
            <form id="dataForm" name="dataForm">
                <input id="pageSize" name="pageSize" value="${pageSize}" type="hidden" />
                <input id="startIndex" name="startIndex" value="${startIndex}" type="hidden" />
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />

                <table class="nonDataTable">
                    <tr>
                        <td class="width50" rowspan="3">
                            <table>
                                <tr>
                                    <th><fmt:message key="audit.availableGroupDivisions"/></th>
                                    <th>&#160;</th>
                                    <th><fmt:message key="audit.selectedGroupDivisions"/></th>
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
                                        <div id="auditGroupDivisions" class="selectionPanel">
                                            <c:forEach items="${auditGroupDivisions}" var="item">
                                                <div id="auditGroupDivisions${item.id}" onclick="YAHOO.ats.toggleClass(this,'selectionActive');">
                                                    <input type="hidden" name="auditGroupDivisions" value="${item.group.id},${item.division.id}" />${item.name}
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="filterLabel"><fmt:message key="audit.title"/></td>
                        <td class="width50" colspan="3">
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
                    </tr>
                    <tr>
                        <td class="filterLabel"><fmt:message key="audit.issuedDate.from"/></td>
                        <td class="width25">
                            <table>
                                <tr>
                                    <td class="width80">
                                        <div id="filter1">
                                            <input id="f_date_issuedDateFrom" class="filterInput" type="text" name="issuedDateFrom" value="${issuedDateFrom}" title="on or after date" readonly="readonly"/>
                                        </div>
                                    </td>
                                    <td class="width20">
                                        <input type="button" id="resetIssuedDateFrom" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="filterLabel"><fmt:message key="audit.issuedDate.to"/></td>
                        <td class="width25">
                            <table>
                                <tr>
                                    <td class="width80">
                                        <div id="filter2">
                                            <input id="f_date_issuedDateTo" class="filterInput" type="text" name="issuedDateTo" value="${issuedDateTo}" title="on or before date" readonly="readonly"/>
                                        </div>
                                    </td>
                                    <td class="width20">
                                        <input type="button" id="resetIssuedDateTo" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="filterLabel"><fmt:message key="reportType.name"/></td>
                        <td colspan="3">
                            <table>
                                <tr>
                                    <td>
                                        <div id="filter3">
                                            <input id="filter1Hidden" type="hidden" name="reportTypeId" value="${reportTypeId}"/>
                                            <input id="filter1Input" class="filterInput" type="text" name="reportTypeName" value="${reportTypeName}" />&#160;
                                            <div id="filter1Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <td class="width3em yui-ac">
                                        <span id="filter1Toggle" class="filterToggle">&#160;</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="ats-h2">
                            <a id="auditHelp" href="${auditHelpUrl}" target="_blank"><fmt:message key="audit.help"/></a>
                        </td>
                        <td colspan="4">
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
        <script type="text/javascript" src="js/module/audit/main.js" />
    </div>

</jsp:root>