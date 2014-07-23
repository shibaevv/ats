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
                                        <div id="filter2">
                                            <input id="filter2Hidden" type="hidden" name="searchUserId" value="${searchUserId}" />
                                            <input id="filter2Input" class="filterInput" type="text" name="searchUserName" value="${searchUserName}" />&#160;
                                            <div id="filter2Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <td class="width3em yui-ac">
                                        <span id="filter2Toggle" class="filterToggle">&#160;</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="width8em" valign="top">
                            <div id="toolbarDiv">&#160;</div>
                        </td>
                    </tr>
                </table>
                <input id="pageSize" name="pageSize" value="${pageSize}" type="hidden" />
                <input id="startIndex" name="startIndex" value="${startIndex}" type="hidden" />
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />
            </form>
        </ats:window>
        <div id="dataCenter" class="ats-container">
            <div id="dataDiv">&#160;</div>
        </div>
        <div id="dataFooter" class="ats-container">
            <div id="paginatorDiv">&#160;</div>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/user/main.js" />
    </div>

</jsp:root>