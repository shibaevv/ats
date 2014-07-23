<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
<fmt:message var="title" key="menu.setup" />
"title":"${title}",
"menuItems":[
{"id":"reference","text":"Reference Data","itemdata":[
{"text":"Business Status","url":"setup/businessStatus/main.do"}
]},
{"id":"security","text":"Security","itemdata":[
{"text":"Roles","url": "setup/role/main.do"},
{"text":"Functions","url": "setup/function/main.do"}
]},
{"id":"template","text":"Template","itemdata":[
{"text":"Templates","url": "setup/template/main.do"}
]}
]
}
</jsp:root>