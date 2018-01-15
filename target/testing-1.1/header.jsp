<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="ua.com.testing.util.UtilConstants" %>

<c:set var="currentLocale"
       value="${not empty param[UtilConstants.REQUEST_LOCALE_PARAM] ? param[UtilConstants.REQUEST_LOCALE_PARAM] :
               (not empty sessionScope[UtilConstants.CURRENT_LOCALE] ? sessionScope[UtilConstants.CURRENT_LOCALE] :
               pageContext.request.locale)}"
       scope="session"/>

<fmt:setLocale value="${sessionScope[UtilConstants.CURRENT_LOCALE]}"/>
<fmt:setBundle basename="${UtilConstants.MESSAGES_PROPERTIES}"/>

<html>
    <h2 class="header-greeting">
        <fmt:message key="welcome"/> <b>${sessionScope.currentUser.name} ${sessionScope.currentUser.surname}</b>
    </h2>

    <a class="service-button" id="log-out-btn"
       href="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}?${UtilConstants.CONTROLLER_KEY}=${UtilConstants.LOG_OUT_CONTROLLER_KEY}">
        <fmt:message key="log.out"/>
    </a>

</html>