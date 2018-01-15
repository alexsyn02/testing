package ua.com.testing.controller.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.com.testing.entity.user.User;
import ua.com.testing.entity.user.UserRole;
import ua.com.testing.handler.LogHandler;
import ua.com.testing.service.InputCheckingService;
import ua.com.testing.service.PasswordHashingService;
import ua.com.testing.service.TestService;
import ua.com.testing.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

import static ua.com.testing.util.UtilConstants.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PasswordHashingService.class, LogHandler.class })
@PowerMockIgnore("javax.management.*")
public class LoginControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TestService testService;

    @Mock
    private InputCheckingService inputCheckingService;

    @Mock
    private HttpServletRequest req;

    private LoginController loginController;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        loginController = new LoginController();

        PowerMockito.mockStatic(LogHandler.class);
        PowerMockito.doNothing().when(LogHandler.class, "eventHandler", Mockito.anyString());
    }

    @Test
    public void processRequestValidationFalseShouldReturnIndexPage() throws SQLException {
        Mockito.when(inputCheckingService.checkLoginForm(req)).thenReturn(false);

        Assert.assertEquals(INDEX_PAGE, loginController.processRequest(req));
    }

    @Test
    public void processRequestNoSuchUserShouldReturnIndexPage() throws SQLException {
        Mockito.when(req.getParameter(LOGIN_EMAIL)).thenReturn(Mockito.anyString());
        Mockito.when(inputCheckingService.checkLoginForm(req)).thenReturn(true);
        Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(null);

        Assert.assertEquals(INDEX_PAGE, loginController.processRequest(req));
    }

    @PrepareForTest({ PasswordHashingService.class, LogHandler.class })
    @Test
    public void processRequestShouldReturnStudentPage() throws SQLException {
        prepareTestConditionsForNotNullUserCase(UserRole.STUDENT);

        Assert.assertEquals(STUDENT_PAGE, loginController.processRequest(req));
    }

    @PrepareForTest({ PasswordHashingService.class, LogHandler.class })
    @Test
    public void processRequestShouldReturnTutorTestsPage() throws SQLException {
        prepareTestConditionsForNotNullUserCase(UserRole.TUTOR);

        Assert.assertEquals(TUTOR_TESTS_PAGE, loginController.processRequest(req));
    }

    private void prepareTestConditionsForNotNullUserCase(UserRole userRole) throws SQLException {
        String password = "password";
        User user = new User.Builder().buildUserRole(userRole).buildHashPassword(password).build();

        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(req.getParameter(LOGIN_EMAIL)).thenReturn(Mockito.anyString());
        Mockito.when(req.getParameter(LOGIN_PASSWORD)).thenReturn(password);
        Mockito.when(req.getSession()).thenReturn(session);
        PowerMockito.mockStatic(PasswordHashingService.class);
        PowerMockito.when(PasswordHashingService.hashPassword(Matchers.anyString())).thenReturn(password);
        Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(user);
    }
}
