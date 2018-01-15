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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="lang-buttons">
        <a class="ua-locale" href="tutor-tests.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=uk"></a>
        <a class="en-locale" href="tutor-tests.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=en"></a>
    </div>
    <jsp:include page="header.jsp"/>
    <a class="service-button" href="${pageContext.request.contextPath}${UtilConstants.TUTOR_TESTS_PAGE}">
        <fmt:message key="tutor.tests"/>
    </a>

    <c:choose>
        <c:when test="${requestScope[UtilConstants.TEST_STATUS] eq 'NEW'}">
            <form class="passing-test-form" action="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}" method="get">
                <c:forEach var="question" items="${requestScope[UtilConstants.LIST_OF_QUESTIONS_BY_SUBJECT]}" >
                    <p>
                        <label>
                            <b>${question.description}</b>
                            <input type="checkbox" name="${UtilConstants.CHOSEN_QUESTIONS}" value="${question.id}">
                        </label>
                    </p>
                </c:forEach>
                <p>
                    <label>
                        <b><fmt:message key="expired.date" /></b>
                        <input type="datetime-local" name="${UtilConstants.SET_EXPIRED_DATE}" required>
                    </label>
                </p>
                <input type="hidden" name="${UtilConstants.CONTROLLER_KEY}" value="${UtilConstants.CHECK_TEST_CONTROLLER_KEY}">
                <button class="submit-btn"><fmt:message key="confirm" /></button>
            </form>
        </c:when>
        <c:when test="${requestScope[UtilConstants.TEST_STATUS] eq 'PASSED'}">
            <form class="passing-test-form" action="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}" method="get">
                <ol class="questions">
                    <c:forEach var="question" items="${requestScope[UtilConstants.LIST_OF_QUESTIONS]}">
                        <c:forEach var="given_answer" items="${requestScope[UtilConstants.LIST_OF_GIVEN_ANSWERS]}" >
                            <c:if test="${sessionScope[UtilConstants.TEST_ID] eq given_answer.idTest and question.id eq given_answer.idQuestion}" >
                                <c:set var="answer" value="${question.answer}" />
                                <li>
                                    <p><b>${question.description}</b></p>
                                    <ul class="answers">
                                        <li><b><fmt:message key="right.answer"/></b> <span class="green">${answer.rightAnswer}</span></li>
                                        <li><b><fmt:message key="your.answer"/></b> <span class="${answer.rightAnswer eq given_answer.answer ? 'green' : 'red'}">${given_answer.answer}</span></li>
                                    </ul>
                                </li>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </ol>
                <p>
                    <label>
                        <fmt:message key="set.mark" /> <input name="${UtilConstants.MARK}" type="number" min="0" max="100" value="${mark ne -1 ? mark : ''}" required>
                    </label>
                </p>
                <input type="hidden" name="${UtilConstants.CONTROLLER_KEY}" value="${UtilConstants.CHECK_TEST_CONTROLLER_KEY}">
                <button class="submit-btn"><fmt:message key="confirm" /></button>
            </form>
        </c:when>
        <c:when test="${requestScope[UtilConstants.TEST_STATUS] eq 'NOT_PASSED'}">
            <form class="passing-test-form" action="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}" method="get">
                <ol class="questions">
                    <c:forEach var="question" items="${requestScope[UtilConstants.LIST_OF_QUESTIONS]}">
                        <c:set var="answer" value="${question.answer}" />
                        <li>
                            <p><b>${question.description}</b></p>
                            <c:forEach var="chosenAnswer" items="${answer.answers}" varStatus="loop">
                                <p class="${answer.rightAnswer eq chosenAnswer ? 'green' : ''}">${chosenAnswer}</p>
                            </c:forEach>
                        </li>
                    </c:forEach>
                </ol>
            </form>
        </c:when>
    </c:choose>
</body>
</html>


