<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
    version="2.0">

    <!-- 
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    -->
    <jsp:output doctype-root-element="html"
        doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
        doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />

    <html xmlns="http://www.w3.org/1999/xhtml">

        <head>
            <meta http-equiv="content-type" content="text/html; charset=utf-8" />
            <meta http-equiv="Pragma" content="no-cache" />
            <meta http-equiv="Date" content="now" />
            <meta http-equiv="Cache-Control" content="private,no-store,no-cache,post-check=0,pre-check=0,max-age=3600" />
            <meta http-equiv="Expires" content="now+3600" />

            <link rel="shortcut icon" href="img/logo.ico" />

            <!--style>
                body {visibility: hidden;}
            </style-->

            <link rel="stylesheet" type="text/css" href="css/default.css" />

            <link rel="stylesheet" type="text/css" href="js/yui/assets/skins/sam/skin.css" />
            <script type="text/javascript" src="js/yui/yuiloader-dom-event/yuiloader-dom-event.js">&#160;</script>
            <script type="text/javascript" src="js/yui/container/container-min.js">&#160;</script>

            <title><fmt:message key="application.title"/>&#160;<decorator:title /></title>

            <decorator:head />

        </head>

        <body class="yui-skin-sam">
            <decorator:body />
        </body>

    </html>

</jsp:root>