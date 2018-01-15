<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="testing" uri="/WEB-INF/tag/dateTag" %>
<%@ page import="ua.com.testing.util.UtilConstants" %>

<c:set var="currentLocale"
       value="${not empty param[UtilConstants.REQUEST_LOCALE_PARAM] ? param[UtilConstants.REQUEST_LOCALE_PARAM]
               : (not empty sessionScope[UtilConstants.CURRENT_LOCALE] ? sessionScope[UtilConstants.CURRENT_LOCALE]
               : pageContext.request.locale)}"
       scope="session"/>

<fmt:setLocale value="${sessionScope[UtilConstants.CURRENT_LOCALE]}"/>
<fmt:setBundle basename="${UtilConstants.MESSAGES_PROPERTIES}"/>

<html>
    <head>
        <title><fmt:message key="student.page" /></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
    </head>
    <body>
        <div class="student-page">
            <div class="lang-buttons">
                <a class="ua-locale" href="student-page.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=uk"></a>
                <a class="en-locale" href="student-page.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=en"></a>
            </div>
            <jsp:include page="header.jsp"/>

            <a class="service-button" href="${pageContext.request.contextPath}${UtilConstants.STUDENT_TESTS_PAGE}">
                <fmt:message key="student.tests"/>
            </a>

            <div class="student-class">
                <div class="userinfo-container">
                    <h3><fmt:message key="email" /> ${sessionScope.currentUser.email}</h3>
                    <h3><fmt:message key="mobile.phone.number" /> ${sessionScope.currentUser.mobilePhoneNumber}</h3>
                </div>
            </div>
            <div class="test-container">
                <form class="request-test-form" action="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}" method="get">
                    <c:if test="${requestScope[UtilConstants.SUCCESSFUL_PASSING_TEST] eq true}" >
                        <p class="success-message"><fmt:message key="successful.passing.test" /></p>
                    </c:if>
                    <c:if test="${requestScope[UtilConstants.GETTING_TEST_SUCCESS] eq true}">
                        <p class="success-message"><fmt:message key="success.requesting"/></p>
                    </c:if>
                    <c:if test="${requestScope[UtilConstants.GETTING_TEST_FORM_WRONG_INPUT] eq true}">
                        <p class="error-message"><fmt:message key="wrong.input"/></p>
                    </c:if>
                    <h2><fmt:message key="request.test" /></h2>
                    <c:choose>
                        <c:when test="${sessionScope[UtilConstants.LIST_OF_SUBJECTS] eq null}">
                            <p><fmt:message key="no.subjects" /></p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${sessionScope[UtilConstants.LIST_OF_SUBJECTS]}" var="subject" >
                                <label>
                                    <p>
                                        <input type="radio" name="${UtilConstants.REQUESTED_SUBJECT_ID}" value="${subject.id}">
                                        <b><fmt:message key="subject"/></b> ${subject.description}
                                    </p>
                                </label>
                            </c:forEach>
                            <input type="hidden" name="${UtilConstants.CONTROLLER_KEY}"
                                   value="${UtilConstants.REQUEST_TEST_FORM_CONTROLLER_KEY}">
                            <button class="send-btn"><fmt:message key="send"/></button>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
        </div>
    </body>
</html>