package ua.com.testing.service.impl;

import ua.com.testing.service.InputCheckingService;

import javax.servlet.http.HttpServletRequest;

import static ua.com.testing.util.UtilConstants.*;
import static ua.com.testing.util.UtilData.*;

public class InputCheckingServiceImpl implements InputCheckingService {

    @Override
    public boolean checkRequestingTest(HttpServletRequest req) {

        return req.getParameter(REQUESTED_SUBJECT_ID) != null;
    }

    @Override
    public boolean checkLoginForm(HttpServletRequest req) {
        String email = req.getParameter(LOGIN_EMAIL);
        String password = req.getParameter(LOGIN_PASSWORD);

        return email != null &&
                password != null &&
                email.matches(EMAIL_REGEX) &&
                password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean checkRegistrationForm(HttpServletRequest req) {
        String name = req.getParameter(REG_FIRST_NAME);
        String surname = req.getParameter(REG_SURNAME);
        String email = req.getParameter(REG_EMAIL);
        String mobilePhone = req.getParameter(REG_MOBILE_PHONE);
        String password = req.getParameter(REG_PASSWORD);
        String confirmedPassword = req.getParameter(REG_CONFIRM_PASSWORD);

        return name != null &&
                surname != null &&
                email != null &&
                mobilePhone != null &&
                password != null &&
                confirmedPassword != null &&
                name.matches(NAME_REGEX) &&
                surname.matches(NAME_REGEX) &&
                email.matches(EMAIL_REGEX) &&
                mobilePhone.matches(PHONE_NUMBER_REGEX) &&
                password.matches(PASSWORD_REGEX) &&
                password.equals(confirmedPassword);
    }
}