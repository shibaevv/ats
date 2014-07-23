<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <div id="dataHolder">
        <div id="dataHeader">
            <form id="dataForm" name="dataForm">
                <input id="dir" name="dir" value="${dir}" type="hidden" />
                <input id="sort" name="sort" value="${sort}" type="hidden" />
            </form>
        </div>
        <div id="dataCenter">
            <input id="commentId" value="${entity.id}" type="hidden" />
            <table class="nonDataTable">
                <tr>
                    <td class="ats-label"><fmt:message key="comment.created.date"/></td>
                    <td>
                        <c:set var="createdDate"><fmt:formatDate pattern="${datePattern}" value="${entity.createdDate}" /></c:set>
                        <input class="text" type="text" readonly="readonly" value="${createdDate}" />
                    </td>
                </tr>
                <tr>
                    <td class="ats-label"><fmt:message key="comment.created.by"/></td>
                    <td>
                        <input class="text" type="text" readonly="readonly" value="${entity.createdBy.fullName}" />
                    </td>
                </tr>
            </table>
            <div class="ats-container">
                <div class="ats-container-hd">
                    <h2><fmt:message key="comment.detail" /></h2>
                </div>
                <div class="ats-container-bd">
                    <ats:text text="${entity.detail}" />
                </div>
            </div>
        </div>
    </div>

</jsp:root>