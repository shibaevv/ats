<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <c:set var="login_url"><c:url value="/login.do" /></c:set>

    <head>
        <!-- to change the content type or response encoding change the following line -->
        <jsp:directive.page contentType="text/html;charset=utf-8"/>
        <title><fmt:message key="access.redirect.title"/></title>
    </head>

    <body>
        <c:redirect url="/login.do" />
    </body>

</jsp:root>