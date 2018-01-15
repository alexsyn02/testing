package ua.com.testing.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.testing.controller.Controller;
import ua.com.testing.entity.test.GivenAnswer;
import ua.com.testing.entity.test.Question;
import ua.com.testing.entity.test.Test;
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

public class TestForTutorController implements Controller {

    private final Logger LOGGER = LogManager.getLogger();
    private final TestService testService;
    private final AnswerService answerService;
    private final QuestionService questionService;

    public TestForTutorController() {
        testService = new TestServiceImpl();
        answerService = new AnswerServiceImpl();
        questionService = new QuestionServiceImpl();
    }

    @Override
    public String processRequest(HttpServletRequest req) throws SQLException {

        HttpSession session = req.getSession();
        User tutor = (User) session.getAttribute(CURRENT_USER);

        if ("".equals(req.getParameter(TEST_ID)) || req.getParameter(TEST_ID) == null) {
            session.setAttribute(WRONG_CHOICE, true);

            LOGGER.info(WRONG_TEST_CHOICE_EVENT + tutor.getId());
            return STUDENT_TESTS_PAGE;
        }

        int idTest = Integer.parseInt(req.getParameter(TEST_ID));

        if ("true".equals(req.getParameter(SET_DECLINED_STATUS))) {
            testService.decline(idTest);

            List<Test> tests = testService.getTestsByUser(tutor);

            req.setAttribute(SUCCESSFUL_TEST_DECLINING, true);
            session.setAttribute(LIST_OF_TESTS_BY_TUTOR, tests);

            LOGGER.info(SUCCESSFUL_DECLINING_TEST_EVENT + idTest);
            return TUTOR_TESTS_PAGE;
        } else {
            Test test = testService.load(Integer.parseInt(req.getParameter(TEST_ID)));
            List<Question> questions = test.getQuestions();
            List<Question> questionsBySubject = questionService.getQuestionsBySubjectId(test.getSubject().getId());

            req.setAttribute(LIST_OF_QUESTIONS, questions);
            req.setAttribute(LIST_OF_QUESTIONS_BY_SUBJECT, questionsBySubject);
            req.setAttribute(TEST_STATUS, test.getTestStatus());
            req.setAttribute(MARK, test.getMark());

            session.setAttribute(TEST_ID, test.getId());

            if (testService.checkTestForPassedStatus(test)) {
                List<GivenAnswer> givenAnswers = answerService.getGivenAnswersByTestId(test.getId());
                req.setAttribute(LIST_OF_GIVEN_ANSWERS, givenAnswers);
            }
        }

        LOGGER.info(SUCCESSFUL_CHOOSING_TEST_EVENT + idTest);
        return CHECK_TEST_PAGE;
    }
}
