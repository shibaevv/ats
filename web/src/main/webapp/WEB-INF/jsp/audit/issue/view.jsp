<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder">
        <div id="dataCenter" class="ats-container">
            <form id="dataForm" name="dataForm">
                <input id="auditId" value="${entity.audit.id}" type="hidden" />
                <input id="issueId" value="${entity.id}" type="hidden" />
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />
            </form>
            <div class="ats-container">
                <div class="ats-container-hd">
                    <h2><fmt:message key="audit.title"/> - <a id="homeAudit" href="#" title="Go to report">${entity.audit.name}</a></h2>
                </div>
                <div class="ats-container-hd">
                    <h2><fmt:message key="issue.title"/> - ${entity.name}</h2>
                </div>
                <div class="ats-container-bd">
                    <table class="nonDataTable">
                        <tr>
                            <td class="width50 valign-top">
                                <table>
                                    <tr>
                                        <td>
                                            <table class="fieldset">
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="issue.risk"/></td>
                                                    <td>${entity.risk.name}</td>
                                                </tr>
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="issue.risk.category"/></td>
                                                    <td>${entity.risk.category.name}</td>
                                                </tr>
                                                <tr>
                                                    <td class="ats-label"><fmt:message key="issue.rating"/></td>
                                                    <td>${entity.rating.name}</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td class="width50 valign-top">
                                <!--table>
                                    <tr>
                                        <td>
                                            <table class="fieldset">
                                                <tr>
                                                    <td class="ats-label"></td>
                                                    <td>
        
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table-->
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <fmt:message var="title" key="issue.detail" />
            <ats:window title="${title}">
                <ats:text text="${entity.detail}" />
                <!--pre>${entity.detail}</pre-->
            </ats:window>
            <fmt:message var="title" key="issue.actions" />
            <ats:window title="${title}">
                <div id="dataDiv">&#160;</div>
            </ats:window>
        </div>

        <!-- info which scripts to load -->
        <script type="text/javascript" src="js/module/audit/issue/view.js" />
    </div>

</jsp:root>