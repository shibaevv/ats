<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<!--jsp:directive.page contentType="application/json;charset=UTF-8" /-->
<jsp:directive.page contentType="text/plain;charset=UTF-8" />
{"data":{"dueDate":"<fmt:formatDate pattern='${datePattern}' value='${action.dueDate}' />","closedDate":"<fmt:formatDate pattern='${datePattern}' value='${action.closedDate}' />","followupDate":"<fmt:formatDate pattern='${datePattern}' value='${action.followupDate}' />","actionFollowupStatusId":"${action.followupStatus.actionFollowupStatusId}","actionStatusId":"${action.businessStatus.actionStatus.actionStatusId}"}}
</jsp:root>