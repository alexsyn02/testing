package ua.com.testing.controller.impl;

import ua.com.testing.controller.Controller;
import ua.com.testing.entity.test.Question;
import ua.com.testing.entity.user.User;
import ua.com.testing.service.AnswerService;
import ua.com.testing.service.QuestionService;
import ua.com.testing.service.TestService;
import ua.com.testing.service.impl.AnswerServiceImpl;
import ua.com.testing.service.impl.QuestionServiceImpl;
import ua.com.testing.service.impl.TestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

import static ua.com.testing.util.UtilConstants.*;

public class PassTestController implements Controller {

    private final TestService testService;
    private final AnswerService answerService;

    public PassTestController() {
        testService = new TestServiceImpl();
        answerService = new AnswerServiceImpl();
    }

    @Override
    public String processRequest(HttpServletRequest req) throws SQLException {

        HttpSession session = req.getSession();
        User student = (User) req.getSession().getAttribute(CURRENT_USER);

        testService.updateTestAnswers(req, session);

        session.removeAttribute(LIST_OF_QUESTIONS);
        session.removeAttribute(TEST_ID);
        session.setAttribute(LIST_OF_TESTS, testService.getTestsByUser(student));

        req.setAttribute(SUCCESSFUL_PASSING_TEST, true);

        return STUDENT_PAGE;
    }
}
