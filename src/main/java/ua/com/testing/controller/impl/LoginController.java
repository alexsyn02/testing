package ua.com.testing.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.testing.controller.Controller;
import ua.com.testing.entity.test.Subject;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.user.User;
import ua.com.testing.entity.user.UserRole;
import ua.com.testing.handler.LogHandler;
import ua.com.testing.service.*;
import ua.com.testing.service.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

import static ua.com.testing.util.UtilConstants.*;

public class LoginController implements Controller {

    private final Logger LOGGER = LogManager.getLogger();
    private final UserService userService;
    private final SubjectService subjectService;
    private final TestService testService;
    private final InputCheckingService inputCheckingService;

    public LoginController() {
        userService = new UserServiceImpl();
        testService = new TestServiceImpl();
        subjectService = new SubjectServiceImpl();
        inputCheckingService = new InputCheckingServiceImpl();
    }

    @Override
    public String processRequest(HttpServletRequest req) throws SQLException {

        String result;
        String eventMessage;

        if (inputCheckingService.checkLoginForm(req)) {
            User user = userService.findByEmail(req.getParameter(LOGIN_EMAIL).toLowerCase());
            HttpSession session = req.getSession();
            String hashPassword = PasswordHashingService.hashPassword(req.getParameter(LOGIN_PASSWORD));

            if (user == null || !user.getHashPassword().equals(hashPassword)) {
                req.setAttribute(LOGIN_ERROR, true);
                req.removeAttribute(LOGIN_FORM_WRONG_INPUT);
                req.setAttribute(ENTERED_EMAIL, req.getParameter(LOGIN_EMAIL));
                result = INDEX_PAGE;

                eventMessage = LOGIN_EVENT_ERROR;
            } else {

                user.setHashPassword(null);

                session.setAttribute(CURRENT_USER, user);

                List<Test> tests = testService.getTestsByUser(user);

                if (userService.verifyTutor(user)) {
                    session.setAttribute(LIST_OF_TESTS_BY_TUTOR, tests);
                } else {
                    List<Subject> subjects = subjectService.getAllSubjects();
                    session.setAttribute(LIST_OF_TESTS, tests);
                    session.setAttribute(LIST_OF_SUBJECTS, subjects);
                }

                result = user.getUserRole().equals(UserRole.STUDENT) ? STUDENT_PAGE : TUTOR_TESTS_PAGE;

                eventMessage = LOGIN_EVENT_SUCCESS + user.getId();
            }
        } else {
            req.setAttribute(LOGIN_FORM_WRONG_INPUT, true);
            req.setAttribute(ENTERED_EMAIL, req.getParameter(LOGIN_EMAIL));
            req.removeAttribute(LOGIN_ERROR);
            result = INDEX_PAGE;

            eventMessage = LOGIN_EVENT_WRONG_INPUT;
        }

        LOGGER.info(eventMessage);
        return result;
    }
}
