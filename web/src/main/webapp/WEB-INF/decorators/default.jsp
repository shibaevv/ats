<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
    version="2.0">

    <!-- 
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
    -->
    <jsp:output doctype-root-element="html"
        doctype-public="-//W3C//DTD HTML 4.01//EN"
        doctype-system="http://www.w3.org/TR/html4/strict.dtd" />

    <html>

        <head>
            <meta http-equiv="content-type" content="text/html; charset=utf-8" />
            <meta http-equiv="Pragma" content="no-cache" />
            <meta http-equiv="Date" content="now" />
            <meta http-equiv="Cache-Control" content="private,no-store,no-cache,post-check=0,pre-check=0,max-age=3600" />
            <meta http-equiv="Expires" content="now+3600" />

            <link rel="shortcut icon" href="img/logo.ico" />

            <style>
                body {visibility: hidden;}
            </style>

            <link rel="stylesheet" type="text/css" href="js/jscalendar-1.0/calendar-blue2.css" />
            <script type="text/javascript" src="js/jscalendar-1.0/all-calendar_stripped.js">&#160;</script>

            <script type="text/javascript" src="js/yui/yuiloader-dom-event/yuiloader-dom-event.js">&#160;</script>

            <title><fmt:message key="application.title"/></title>

            <decorator:head />

        </head>

        <body class="yui-skin-sam">
            <iframe id="yui-history-iframe" src="js/yui/history/assets/blank.html">&#160;</iframe>
            <input id="yui-history-field" type="hidden" />

            <div id="doc3">
                <div id="hd" role="banner">
                    <table class="width100">
                        <tr id="banner">
                            <td><jsp:include page="/WEB-INF/decorators/header.jsp" /></td>
                        </tr>
                        <tr>
                            <td><div id="mainMenuDiv">&#160;</div></td>
                        </tr>
                    </table>
                </div>
                <div id="bd">
                    <jsp:include page="/WEB-INF/decorators/menuCenter.jsp" />
                    <div id="center">&#160;</div>
                </div>
            </div>

            <div id="editDiv">
                <div class="hd"></div>
                <div class="bd"></div>
                <div class="ft"></div>
            </div>
            <div id="alertDiv">
                <div class="hd"></div>
                <div class="bd"></div>
                <div class="ft"></div>
            </div>
        </body>

        <script type="text/javascript" src="js/module/default.js">&#160;</script>

    </html>

</jsp:root>