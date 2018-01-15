package ua.com.testing.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.testing.controller.Controller;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.user.User;
import ua.com.testing.handler.LogHandler;
import ua.com.testing.service.InputCheckingService;
import ua.com.testing.service.TestService;
import ua.com.testing.service.impl.InputCheckingServiceImpl;
import ua.com.testing.service.impl.TestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

import static ua.com.testing.util.UtilConstants.*;

public class RequestTestFormController implements Controller {

    private final TestService testService;
    private final InputCheckingService inputCheckingService;
    private final Logger LOGGER = LogManager.getLogger();

    public RequestTestFormController() {
        testService = new TestServiceImpl();
        inputCheckingService = new InputCheckingServiceImpl();
    }

    @Override
    public String processRequest(HttpServletRequest req) throws SQLException {

        HttpSession session = req.getSession();
        User student = (User) session.getAttribute(CURRENT_USER);
        String eventMessage;

        if (inputCheckingService.checkRequestingTest(req)) {

            int id_subject = Integer.parseInt(req.getParameter(REQUESTED_SUBJECT_ID));
            testService.processRequestingTest(student, id_subject);
            session.setAttribute(LIST_OF_TESTS, testService.getTestsByUser(student));
            req.setAttribute(GETTING_TEST_SUCCESS, true);
            eventMessage = REQUEST_TEST_EVENT_SUCCESS + student.getId();

        } else {
            req.setAttribute(GETTING_TEST_FORM_WRONG_INPUT, true);
            eventMessage = REQUEST_TEST_EVENT_WRONG_INPUT + student.getId();
        }

        LOGGER.info(eventMessage);
        return STUDENT_PAGE;
    }
}
