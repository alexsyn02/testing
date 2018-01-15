<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="ua.com.testing.util.UtilConstants" %>

<c:set var="currentLocale"
       value="${not empty param[UtilConstants.REQUEST_LOCALE_PARAM] ? param[UtilConstants.REQUEST_LOCALE_PARAM] :
               (not empty sessionScope[UtilConstants.CURRENT_LOCALE] ? sessionScope[UtilConstants.CURRENT_LOCALE]
               : pageContext.request.locale)}"
       scope="session"/>

<fmt:setLocale value="${sessionScope[UtilConstants.CURRENT_LOCALE]}"/>
<fmt:setBundle basename="${UtilConstants.MESSAGES_PROPERTIES}"/>

<html>

  <head>
    <title><fmt:message key="log.in" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
  </head>

  <body>
    <div class="login-page">

      <div class="lang-buttons">
        <a class="ua-locale" href="index.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=uk"></a>
        <a class="en-locale" href="index.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=en"></a>
      </div>

      <div class="login-container">
        <h1><fmt:message key="log.in" /></h1>
        <c:if test="${requestScope[UtilConstants.LOGIN_ERROR] eq true}">
          <p class="error-message"><fmt:message key="login.error"/></p>
        </c:if>
        <c:if test="${requestScope[UtilConstants.LOGIN_FORM_WRONG_INPUT] eq true}" >
          <p class="error-message"><fmt:message key="wrong.login.input"/></p>
        </c:if>
        <form class="login-form" action="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}" method="get">
          <input type="email" name="${UtilConstants.LOGIN_EMAIL}" placeholder="<fmt:message key="enter.email" />"
                 value="${requestScope[UtilConstants.LOGIN_EMAIL]}" required autofocus>
          <input type="password" name="${UtilConstants.LOGIN_PASSWORD}" placeholder="<fmt:message key="enter.password" />"
                 required="required">
          <input type="hidden" name="${UtilConstants.CONTROLLER_KEY}" value="${UtilConstants.LOG_IN_CONTROLLER_KEY}">
          <button id="login-button"><fmt:message key="log.in"/></button>
        </form>
        <a href="${pageContext.request.contextPath}${UtilConstants.REGISTRATION_PAGE}">
          <fmt:message key="no.account"/></a>
      </div>

    </div>

  </body>
</html>
