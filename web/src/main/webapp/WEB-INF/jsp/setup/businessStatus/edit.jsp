<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">

    <form name="businessStatus"
        action="setup/businessStatus/edit.do?readOnly=false">
        <input type="hidden" id="businessStatusId" name="businessStatusId" value="${entity.businessStatusId}" />

        <table>
            <tr class="error-hidden">
                <td id="errors.message">&#160;</td>
            </tr>
            <tr class="error-hidden">
                <td id="errors.businessStatus">&#160;</td>
            </tr>
        </table>
        <table class="nonDataTable">
            <tr class="error-hidden">
                <td />
                <td id="errors.name">&#160;</td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="businessStatus.name" /></td>
                <td>
                    <input type="text" name="name" value="${entity.name}" class="text" />
                </td>
            </tr>
        </table>
    </form>

</jsp:root>