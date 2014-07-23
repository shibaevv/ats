<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">

    <form name="function"
        action="setup/function/edit.do?readOnly=false">
        <input type="hidden" id="functionId" name="functionId" value="${entity.functionId}" />

        <table>
            <tr class="error-hidden"><td id="errors.message">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.function">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.name">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.module">&#160;</td></tr>
            <tr class="error-hidden"><td id="errors.query">&#160;</td></tr>
        </table>
        <table class="nonDataTable left">
            <tr>
                <td class="ats-label"><fmt:message key="function.name" /></td>
                <td>
                    <input type="text" name="name" value="${entity.name}" class="text" />
                </td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="function.module" /></td>
                <td>
                    <input type="text" name="module" value="${entity.module}" class="text" />
                </td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="function.query" /></td>
                <td>
                    <input type="text" name="query" value="${entity.query}" class="text" />
                </td>
            </tr>
            <tr>
                <td class="ats-label"><fmt:message key="function.home" /></td>
                <td>
                    <input type="checkbox" name="home" value="${entity.home}" class="checkbox" />
                </td>
            </tr>
        </table>
    </form>

</jsp:root>