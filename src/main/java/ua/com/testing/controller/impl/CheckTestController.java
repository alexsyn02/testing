package ua.com.testing.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.testing.controller.Controller;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.user.User;
import ua.com.testing.service.TestService;
import ua.com.testing.service.impl.TestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ua.com.testing.util.UtilConstants.*;

public class CheckTestController implements Controller {

    private final TestService testService;
    private final Logger LOGGER = LogManager.getLogger();

    public CheckTestController() {
        testService = new TestServiceImpl();
    }

    @Override
    public String processRequest(HttpServletRequest req) throws SQLException {

        HttpSession session = req.getSession();
        User tutor = (User) session.getAttribute(CURRENT_USER);

        String[] questions = req.getParameterValues(CHOSEN_QUESTIONS);
        int idTest = Integer.parseInt(String.valueOf(session.getAttribute(TEST_ID)));
        String expiredDate = req.getParameter(SET_EXPIRED_DATE);

        if (questions == null) {
            if (req.getParameter(MARK) == null) {

                req.setAttribute(WRONG_CHOICE, true);
                LOGGER.info(WRONG_CHOICE_MARK_EVENT + idTest);

            } else {
                int mark = Integer.parseInt(req.getParameter(MARK));

                testService.setMark(idTest, mark);

                List<Test> tests = testService.getTestsByUser(tutor);

                req.setAttribute(SUCCESSFUL_SETTING_MARK, true);
                session.setAttribute(LIST_OF_TESTS_BY_TUTOR, tests);
                LOGGER.info(SUCCESSFUL_SETTING_MARK_EVENT + idTest);
            }
        } else {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(expiredDate.replace("T", " "), formatter);

            if (! (dateTime.compareTo(LocalDateTime.now()) == 1)) {

                req.setAttribute(WRONG_DATE, true);
                LOGGER.info(WRONG_DATE_EVENT + idTest);
            } else {

                for (String question : questions) {
                    int idQuestion = Integer.parseInt(question);
                    testService.insertQuestionIntoTest(idTest, idQuestion);
                }

                testService.updateTestStatusAsNotPassed(idTest);
                testService.setExpiredDate(idTest, Timestamp.valueOf(expiredDate.replace("T", " ").concat(":00")));

                session.removeAttribute(TEST_ID);
                session.setAttribute(LIST_OF_TESTS_BY_TUTOR, testService.getTestsByUser(tutor));

                req.setAttribute(SUCCESSFUL_SENDING_QUESTS, true);

                LOGGER.info(SUCCESSFUL_SENDING_QUESTIONS_EVENT + idTest);
            }
        }
        return TUTOR_TESTS_PAGE;
    }
}
