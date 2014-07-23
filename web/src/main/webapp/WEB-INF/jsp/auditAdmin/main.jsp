<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder">
        <fmt:message var="title" key="menu.reportAdmin" />
        <ats:window id="dataHeader" title="${title}">
            <form id="dataForm" name="dataForm">
                <table class="nonDataTable">
                    <tr>
                        <td class="filterLabel"><fmt:message key="audit.admin.title"/></td>
                        <td class="width50em">
                            <table>
                                <tr>
                                    <td>
                                        <div id="filter0">
                                            <input id="filter0Hidden" type="hidden" name="searchAuditId" value="${searchAuditId}" />
                                            <input id="filter0Input" class="filterInput" type="text" name="searchAuditName" value="${searchAuditName}" title="Report Name" />&#160;
                                            <div id="filter0Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <!--td class="width3em yui-ac">
                                        <span id="filter0Toggle" class="filterToggle">&#160;</span>
                                    </td-->
                                </tr>
                            </table>
                        </td>
                        <td>
                            <div id="toolbarDiv"></div>
                        </td>
                    </tr>
                </table>
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />
            </form>
        </ats:window>
        <div id="dataCenter" class="ats-container">
            <div id="dataDiv"></div>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/auditAdmin/main.js" />
    </div>

</jsp:root>