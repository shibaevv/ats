<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:form="http://www.springframework.org/tags/form"
    version="2.0">

    <head>
        <!-- to change the content type or response encoding change the following line -->
        <jsp:directive.page contentType="text/html; charset=utf-8" />

        <title><fmt:message key="login.title"/></title>

        <script language="javascript" type="text/javascript">
            YAHOO.namespace('ats');
            YAHOO.util.Event.addListener(window, "load", function() {
                // Instantiate a Panel from markup
                YAHOO.ats.login = new YAHOO.widget.Panel("loginDiv", {
                    fixedcenter:true,
                    width:"400px",
                    height:"250px",
                    visible:true,
                    draggable:false,
                    close:false
                });
                YAHOO.ats.login.render();
                //
                document.f.j_username.focus();
            });
        </script>
    </head>

    <body>
        <div id="headerDiv">
            <div><fmt:message key="login.header"/></div>
        </div>
        <form name="f" action="j_spring_security_check" method="post">
            <div id="loginDiv">
                <div class="bd">
                    <table class="nonDataTable">
                        <tr>
                            <td id="loginMsg" >
                                   <label><fmt:message key="login.detail"/></label>
                            </td>
                        </tr>
                        <c:if test="${not empty param.login_error}">
                            <tr class="ats-h3" style="color:red;">
                                <td>
                                    <fmt:message key="login.error.message"/>
                                    <br/>
                                    <fmt:message key="login.error.reason">
                                        <fmt:param>${SPRING_SECURITY_LAST_EXCEPTION.message}</fmt:param>
                                    </fmt:message>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                    <table class="nonDataTable">
                        <tr>
                            <td class="ats-label"><fmt:message key="login.user"/></td>
                            <td>
                                <input type="text" class="text" name="j_username" value="${not empty param.login_error ? SPRING_SECURITY_LAST_USERNAME : ''}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ats-label"><fmt:message key="login.password"/></td>
                            <td>
                                <input type="password" class="text" name="j_password" />
                            </td>
                        </tr>
                        <tr>
                            <td />
                            <td>
                                <input name="submit" type="submit" class="login-button" value="Login"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </form>
    </body>

</jsp:root>