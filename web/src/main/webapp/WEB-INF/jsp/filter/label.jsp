<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<c:forEach items="${entities}" var="entity" varStatus="status">${entity}<c:if test="${not status.last}">,</c:if></c:forEach>
<c:if test="${not empty entity}">${entity}</c:if>
</jsp:root>