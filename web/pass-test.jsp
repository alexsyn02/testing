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
    <title>Title</title>
    <link type="text/css" href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
    <div class="lang-buttons">
        <a class="ua-locale" href="student-tests.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=uk"></a>
        <a class="en-locale" href="student-tests.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=en"></a>
    </div>
    <jsp:include page="header.jsp"/>

    <a id="student-page-btn" class="service-button" href="${pageContext.request.contextPath}${UtilConstants.STUDENT_PAGE}">
        <fmt:message key="student.page" />
    </a>
    <a class="service-button" href="${pageContext.request.contextPath}${UtilConstants.STUDENT_TESTS_PAGE}">
        <fmt:message key="student.tests"/>
    </a>

    <c:choose>
        <c:when test="${requestScope[UtilConstants.TEST_STATUS] eq 'NEW'}">
            <h3><fmt:message key="test.not.prepared" /></h3>
            <h3><fmt:message key="check.it.later" /></h3>
        </c:when>
        <c:when test="${requestScope[UtilConstants.TEST_STATUS] eq 'DECLINED'}">
            <h3><fmt:message key="test.was.declined" /></h3>
            <h3><fmt:message key="apologies" /></h3>
        </c:when>
        <c:when test="${requestScope[UtilConstants.TEST_STATUS] eq 'PASSED'}">
            <ol class="questions">
                <c:forEach var="question" items="${sessionScope[UtilConstants.LIST_OF_QUESTIONS]}">
                    <c:forEach var="given_answer" items="${requestScope[UtilConstants.LIST_OF_GIVEN_ANSWERS]}" >
                        <c:if test="${sessionScope[UtilConstants.TEST_ID] eq given_answer.idTest and question.id eq given_answer.idQuestion}" >
                            <c:set var="answer" value="${question.answer}" />
                                <li>
                                    <p><b>${question.description}</b></p>
                                    <ul class="answers">
                                        <c:set var="mark" value="${requestScope[UtilConstants.MARK]}" />
                                        <li><b><fmt:message key="your.answer"/></b> <span class="${answer.rightAnswer eq given_answer.answer and mark ne -1 ? 'green' : mark eq -1 ? '' : 'red'}">${given_answer.answer}</span></li>
                                        <c:if test="${mark ne -1}" >
                                            <li><b><fmt:message key="right.answer"/></b> <span class="green">${answer.rightAnswer}</span></li>
                                        </c:if>
                                    </ul>
                                </li>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </ol>
        </c:when>
        <c:otherwise>
            <form class="passing-test-form" action="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}" method="get">
                <ol class="questions">
                    <c:forEach var="question" items="${sessionScope[UtilConstants.LIST_OF_QUESTIONS]}">
                        <c:set var="answer" value="${question.answer}" />
                            <li>
                                <p><b>${question.description}</b></p>
                                <c:forEach var="chosenAnswer" items="${answer.answers}" varStatus="loop">
                                    <p>
                                        <label>
                                                ${chosenAnswer} <input type="radio" name="${answer.idQuestion}"
                                                                       value="${chosenAnswer}" required>
                                        </label>
                                    </p>
                                </c:forEach>
                            </li>
                    </c:forEach>
                </ol>
                <input type="hidden" name="${UtilConstants.CONTROLLER_KEY}" value="${UtilConstants.PASS_TEST_CONTROLLER_KEY}">
                <button class="submit-btn"><fmt:message key="submit" /></button>
            </form>
        </c:otherwise>
    </c:choose>
</body>
</html>