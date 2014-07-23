<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:ats="/WEB-INF/tld/ats.tld"
    version="2.0">

    <form name="template" enctype="multipart/form-data"
        action="setup/template/edit.do?readOnly=false">
        <input type="hidden" id="templateId" name="templateId" value="${entity.templateId}" />
        <input type="hidden" id="contentType" name="contentType" value="${entity.contentType}" />

        <table>
            <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.template">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.reference">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.name">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.content">&#160;</td></tr>
        </table>
        <table class="nonDataTable left">
            <tr>
                <td class="ats-label"><fmt:message key="template.reference" /></td>
                <td>
                    <input type="text" name="reference" value="${entity.reference}" class="text" readonly="readonly" />
                </td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="template.name" /></td>
                <td>
                    <input type="text" name="name" value="${entity.name}" class="text" />
                </td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="template.detail" /></td>
                <td>
                    <textarea name="detail" class="textarea">${entity.detail}</textarea>
                </td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="template.content" /></td>
                <td>
                    <input type="file" id="attachment" name="attachment" size="70" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <textarea name="contentText" class="textarea" style="height:15em;">${entity.contentText}</textarea>
                </td>
            </tr>
        </table>
    </form>

</jsp:root>