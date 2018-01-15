package ua.com.testing.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.testing.controller.Controller;
import ua.com.testing.entity.test.Subject;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.user.User;
import ua.com.testing.entity.user.UserRole;
import ua.com.testing.handler.LogHandler;
import ua.com.testing.service.InputCheckingService;
import ua.com.testing.service.SubjectService;
import ua.com.testing.service.TestService;
import ua.com.testing.service.UserService;
import ua.com.testing.service.impl.InputCheckingServiceImpl;
import ua.com.testing.service.impl.SubjectServiceImpl;
import ua.com.testing.service.impl.TestServiceImpl;
import ua.com.testing.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

import static ua.com.testing.util.UtilConstants.*;

public class RegistrationController implements Controller {

    private final Logger LOGGER = LogManager.getLogger();
    private final InputCheckingService inputCheckingService;
    private final UserService userService;
    private final TestService testService;
    private final SubjectService subjectService;

    public RegistrationController() {
        inputCheckingService = new InputCheckingServiceImpl();
        userService = new UserServiceImpl();
        testService = new TestServiceImpl();
        subjectService = new SubjectServiceImpl();
    }

    @Override
    public String processRequest(HttpServletRequest req) throws SQLException {

        String result;
        String eventMessage;

        if (inputCheckingService.checkRegistrationForm(req)) {

            User user = userService.createUserFromRequest(req);
            int userId = userService.save(user);

            if (userId != 0) {
                user.setId(userId);
                user.setHashPassword(null);
                user.setUserRole(UserRole.STUDENT);

                HttpSession session = req.getSession();
                List<Test> tests = testService.getTestsByUser(user);
                List<Subject> subjects = subjectService.getAllSubjects();

                session.setAttribute(CURRENT_USER, user);
                session.setAttribute(LIST_OF_TESTS, tests);
                session.setAttribute(LIST_OF_SUBJECTS, subjects);

                eventMessage = REGISTRATION_EVENT_SUCCESS + userId;
                result = STUDENT_PAGE;
            } else {
                req.setAttribute(REGISTRATION_ERROR, true);
                req.removeAttribute(REGISTRATION_FORM_WRONG_INPUT);
                result = REGISTRATION_PAGE;

                eventMessage = REGISTRATION_EVENT_ERROR;
            }
        } else {
            req.setAttribute(REGISTRATION_FORM_WRONG_INPUT, true);
            req.removeAttribute(REGISTRATION_ERROR);
            result = REGISTRATION_PAGE;

            eventMessage = REGISTRATION_EVENT_WRONG_INPUT;
        }

        LOGGER.info(eventMessage);
        return result;
    }
}
