package ua.com.testing.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.testing.controller.Controller;
import ua.com.testing.entity.test.GivenAnswer;
import ua.com.testing.entity.test.Question;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.test.TestStatus;
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

public class TestForStudentController implements Controller {

    private final TestService testService;
    private final AnswerService answerService;
    private final Logger LOGGER = LogManager.getLogger();

    public TestForStudentController() {
        testService = new TestServiceImpl();
        answerService = new AnswerServiceImpl();
    }

    @Override
    public String processRequest(HttpServletRequest req) throws SQLException {

        HttpSession session = req.getSession();
        User student = (User) session.getAttribute(CURRENT_USER);

        if (req.getParameter(TEST_ID) == null) {
            session.setAttribute(WRONG_CHOICE, true);

            LOGGER.info(WRONG_TEST_CHOICE_EVENT + student.getId());
            return STUDENT_TESTS_PAGE;
        }

        Test test = testService.load(Integer.parseInt(req.getParameter(TEST_ID)));
        List<Question> questions = test.getQuestions();

        if (test.getTestStatus().equals(TestStatus.NOT_PASSED)) {
            QuestionService.shuffleAnswers(questions);
        }

        req.setAttribute(MARK, test.getMark());
        req.setAttribute(TEST_STATUS, test.getTestStatus());
        session.setAttribute(LIST_OF_QUESTIONS, questions);
        session.setAttribute(TEST_ID, test.getId());

        if (testService.checkTestForPassedStatus(test)) {
            List<GivenAnswer> givenAnswers = answerService.getGivenAnswersByTestId(test.getId());
            req.setAttribute(LIST_OF_GIVEN_ANSWERS, givenAnswers);
        }

        LOGGER.info(SUCCESSFUL_CHOOSING_TEST_EVENT + test.getId());
        return PASS_TEST_PAGE;
    }
}
