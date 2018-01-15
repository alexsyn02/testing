package ua.com.testing.controller.impl;

import junit.framework.TestCase;
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
import ua.com.testing.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static ua.com.testing.util.UtilConstants.REGISTRATION_PAGE;
import static ua.com.testing.util.UtilConstants.STUDENT_PAGE;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RegistrationController.class)
@PowerMockIgnore("javax.management.*")
public class RegistrationControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    private RegistrationController registrationController;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        registrationController = PowerMockito.spy(new RegistrationController());
    }

    @Test
    public void processRequestValidationFalseShouldReturnRegistrationPage() throws Exception {
        Assert.assertEquals(REGISTRATION_PAGE, registrationController.processRequest(request));
    }

    @Test
    public void processRequestSuchUserExistsShouldReturnRegistrationPage() throws Exception {
        int userNotExistsReturningFromDB = 0;
        prepareTestConditionsValidationTrueCase(userNotExistsReturningFromDB);

        Assert.assertEquals(REGISTRATION_PAGE, registrationController.processRequest(request));
    }

    @Test
    public void processRequestShouldReturnUserPage() throws Exception {
        int userExistsReturningFromDB = 1; //any not 0 number
        prepareTestConditionsValidationTrueCase(userExistsReturningFromDB);

        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);

        Assert.assertEquals(STUDENT_PAGE, registrationController.processRequest(request));
    }

    private void prepareTestConditionsValidationTrueCase(int userSavingReturnValue) throws Exception {
        User user = new User.Builder().build();
        Mockito.when(userService.save(user)).thenReturn(userSavingReturnValue);
    }
}