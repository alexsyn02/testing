<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ua.com.testing.util.UtilConstants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="currentLocale"
       value="${not empty param[UtilConstants.REQUEST_LOCALE_PARAM] ? param[UtilConstants.REQUEST_LOCALE_PARAM]
               : (not empty sessionScope[UtilConstants.CURRENT_LOCALE] ? sessionScope[UtilConstants.CURRENT_LOCALE]
               : pageContext.request.locale)}"
       scope="session"/>

<fmt:setLocale value="${sessionScope[UtilConstants.CURRENT_LOCALE]}"/>
<fmt:setBundle basename="${UtilConstants.MESSAGES_PROPERTIES}"/>

<html>
<head>
    <title><fmt:message key="registration"/></title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<div class="registration-page">

    <div class="lang-buttons">
        <a class="ua-locale" href="registration.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=uk"></a>
        <a class="en-locale" href="registration.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=en"></a>
    </div>

    <div class="login-container">
        <h1><fmt:message key="registration"/></h1>
        <form class="registration-form" action="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}" method="get">
            <input type="text" name="${UtilConstants.REG_FIRST_NAME}" placeholder="<fmt:message key="enter.name"/>"
                   required="required">
            <input type="text" name="${UtilConstants.REG_SURNAME}" placeholder="<fmt:message key="enter.surname"/>"
                   required="required">
            <input type="email" name="${UtilConstants.REG_EMAIL}" placeholder="<fmt:message key="enter.email"/>"
                   required="required">
            <input type="text" name="${UtilConstants.REG_MOBILE_PHONE}" placeholder="<fmt:message key="enter.mobile-phone"/>"
                   required="required">
            <input type="password" name="${UtilConstants.REG_PASSWORD}" placeholder="<fmt:message key="enter.password"/>"
                   required="required">
            <input type="password" name="${UtilConstants.REG_CONFIRM_PASSWORD}" placeholder="<fmt:message key="confirm.password"/>"
                   required="required">
            <input type="hidden" name="${UtilConstants.CONTROLLER_KEY}"
                   value="${UtilConstants.REGISTRATION_CONTROLLER_KEY}">
            <button id="registrationButton"><fmt:message key="register" /></button>
        </form>
        <c:if test="${requestScope[UtilConstants.REGISTRATION_ERROR] eq true}">
            <p class="error-message"><fmt:message key="registration.error"/></p>
        </c:if>
        <c:if test="${requestScope[UtilConstants.REGISTRATION_FORM_WRONG_INPUT] eq true}">
            <p class="error-message"><fmt:message key="registration.wrong.input"/></p>
        </c:if>
        <a href="${pageContext.request.contextPath}${UtilConstants.INDEX_PAGE}"><fmt:message key="have.account"/></a>
    </div>
</div>
</body>
</html>
