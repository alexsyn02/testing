<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="ua.com.testing.util.UtilConstants" %>

<c:set var="currentLocale"
       value="${not empty param[UtilConstants.REQUEST_LOCALE_PARAM] ? param[UtilConstants.REQUEST_LOCALE_PARAM] :
               (not empty sessionScope[UtilConstants.CURRENT_LOCALE] ? sessionScope[UtilConstants.CURRENT_LOCALE] :
               pageContext.request.locale)}"
       scope="session"/>

<fmt:setLocale value="${sessionScope[UtilConstants.CURRENT_LOCALE]}"/>
<fmt:setBundle basename="${UtilConstants.MESSAGES_PROPERTIES}"/>

<html>
<head>
    <title><fmt:message key="tutor.tests"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
    <div class="lang-buttons">
        <a class="ua-locale" href="tutor-tests.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=uk"></a>
        <a class="en-locale" href="tutor-tests.jsp?${UtilConstants.REQUEST_LOCALE_PARAM}=en"></a>
    </div>

    <jsp:include page="header.jsp"/>
    <jsp:useBean id="now" class="java.util.Date"/>

    <div class="test-forms">
        <c:if test="${requestScope[UtilConstants.SUCCESSFUL_SETTING_MARK] eq true}" >
            <p class="success-message"><fmt:message key="success.setting.mark" /></p>
        </c:if>
        <c:if test="${requestScope[UtilConstants.SUCCESSFUL_SENDING_QUESTS] eq true}" >
            <p class="success-message"><fmt:message key="success.sending.tests" /></p>
        </c:if>
        <c:if test="${requestScope[UtilConstants.SUCCESSFUL_TEST_DECLINING] eq true}" >
            <p class="success-message"><fmt:message key="test.was.declined" /></p>
        </c:if>
        <c:if test="${requestScope[UtilConstants.WRONG_CHOICE] eq true}" >
            <p class="error-message"><fmt:message key="wrong.choice" /></p>
        </c:if>
        <c:if test="${requestScope[UtilConstants.WRONG_DATE] eq true}" >
            <p class="error-message"><fmt:message key="wrong.date" /></p>
        </c:if>
        <c:choose>
            <c:when test="${sessionScope[UtilConstants.LIST_OF_TESTS_BY_TUTOR] eq null}">
                <h3><fmt:message key="no.tests" /></h3>
            </c:when>
            <c:otherwise>
                <c:forEach items="${sessionScope[UtilConstants.LIST_OF_TESTS_BY_TUTOR]}" var="test" >
                    <form class="checking-test-form" action="${pageContext.request.contextPath}${UtilConstants.MAIN_SERVLET}" method="get">
                        <c:set var="subject" value="${test.subject}" />
                        <c:set var="student" value="${test.student}" />
                        <div class="test">
                            <h3><b><fmt:message key="subject" /></b> ${subject.description}</h3>
                            <p><b><fmt:message key="student" /></b> ${student.name} ${student.surname}</p>
                            <p><b><fmt:message key="beginning.date" /></b> ${test.beginningDate}</p>
                            <c:choose>
                                <c:when test="${test.expiredDate eq null}" >
                                    <p><b><fmt:message key="expired.date" /></b> <fmt:message key="none" /></p>
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
                                <c:otherwise>
                                    <p><b><fmt:message key="test.status" /></b> <fmt:message key="test.declined" /></p>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${test.mark eq -1}" >
                                    <p><b><fmt:message key="mark" /></b> <fmt:message key="none" /></p>
                                </c:when>
                                <c:otherwise>
                                    <p><b><fmt:message key="mark" /></b> ${test.mark}</p>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${test.testStatus eq 'NEW'}" >
                                    <button class="questions-btn"><fmt:message key="choose.questions" /></button>
                                    <button class="decline-btn" name="${UtilConstants.SET_DECLINED_STATUS}" value="${true}"><fmt:message key="decline" /></button>
                                </c:when>
                                <c:when test="${test.testStatus eq 'NOT_PASSED'}" >
                                    <input class="${test.expiredDate lt now and test.mark eq -1 ? 'expired-date-fail-btn' : 'questions-btn'}"
                                           type="submit" value="<fmt:message key="show.questions" />">
                                </c:when>
                                <c:when test="${test.testStatus eq 'PASSED'}" >
                                    <button class="questions-btn" type="submit"><fmt:message key="check.answers" /></button>
                                </c:when>
                            </c:choose>
                            <input type="hidden" name="${UtilConstants.TEST_ID}" value="${test.id}">
                            <input type="hidden" name="${UtilConstants.CONTROLLER_KEY}" value="${UtilConstants.TEST_FOR_TUTOR_CONTROLLER_KEY}">
                        </div>
                    </form>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
