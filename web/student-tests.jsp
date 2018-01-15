<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title><fmt:message key="student.tests" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
    <div class="student-class">

        <div class="lang-buttons">
            <a class="ua-locale" href="student-tests.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=uk"></a>
            <a class="en-locale" href="student-tests.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=en"></a>
        </div>
        <jsp:include page="header.jsp"/>

        <a id="student-page-btn" class="service-button" href="${pageContext.request.contextPath}${UtilConstants.STUDENT_PAGE}">
            <fmt:message key="student.page" />
        </a>

        <div class="student-tests-container">
            <jsp:useBean id="now" class="java.util.Date"/>
            <c:if test="${UtilConstants.WRONG_CHOICE}" >
                <p class="error-message"><fmt:message key="wrong.choice" /></p>
            </c:if>
            <c:choose>
                <c:when test="${sessionScope[UtilConstants.LIST_OF_TESTS] eq null}" >
                    <fmt:message key="no.tests" />
                </c:when>
                <c:otherwise>
                    <c:forEach items="${sessionScope[UtilConstants.LIST_OF_TESTS]}" var="test" >
                        <form class="test-form" action="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}" method="get">
                            <c:set var="subject" value="${test.subject}" />
                            <c:set var="tutor" value="${test.tutor}" />
                            <h3><b><fmt:message key="subject" /></b> ${subject.description}</h3>
                            <p><b><fmt:message key="tutor" /></b> ${tutor.name} ${tutor.surname}</p>
                            <p><b><fmt:message key="beginning.date" /></b> ${test.beginningDate}</p>
                            <c:choose>
                                <c:when test="${test.expiredDate eq null}" >
                                    <p><b><fmt:message key="expired.date" /></b> <fmt:message key="none" /></p>
                                </c:when>
                                <c:when test="${test.expiredDate lt now and test.testStatus eq 'NOT_PASSED'}" >
                                    <p class="red"><b><fmt:message key="expired.date" /></b> ${test.expiredDate}</></p>
                                </c:when>
                                <c:otherwise>
                                    <p><b><fmt:message key="expired.date" /></b> ${test.expiredDate}</p>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${test.testStatus eq 'NEW'}" >
                                    <p><b><fmt:message key="test.status" /></b> <fmt:message key="new.test" /></p>
                                </c:when>
                                <c:when test="${test.testStatus eq 'PASSED'}" >
                                    <p><b><fmt:message key="test.status" /></b> <fmt:message key="test.passed" /></p>
                                </c:when>
                                <c:when test="${test.testStatus eq 'NOT_PASSED'}" >
                                    <p><b><fmt:message key="test.status" /></b> <fmt:message key="test.not.passed" /></p>
                                </c:when>
                                <c:when test="${test.testStatus eq 'DECLINED'}">
                                    <p><b><fmt:message key="test.status" /></b> <span class="red"><fmt:message key="test.declined" /></span></p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${test.mark eq -1}" >
                                    <p><b><fmt:message key="mark" /></b> <fmt:message key="none" /></p>
                                </c:when>
                                <c:otherwise>
                                    <p><b><fmt:message key="mark" /></b> ${test.mark}</p>
                                </c:otherwise>
                            </c:choose>
                            <input type="hidden" name="${UtilConstants.TEST_ID}" value="${test.id}">
                            <input type="hidden" name="${UtilConstants.CONTROLLER_KEY}" value="${UtilConstants.TEST_FOR_STUDENT_CONTROLLER_KEY}">
                            <c:choose>
                                <c:when test="${test.testStatus eq 'PASSED'}">
                                    <button class="send-btn"><fmt:message key="test.results" /></button>
                                </c:when>
                                <c:when test="${test.expiredDate lt now}">
                                    <button class="service-button" disabled><fmt:message key="pass.test" /></button>
                                </c:when>
                                <c:otherwise>
                                    <button class="service-button"><fmt:message key="pass.test" /></button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</body>
</html>