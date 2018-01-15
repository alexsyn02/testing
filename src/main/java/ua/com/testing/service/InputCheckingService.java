package ua.com.testing.service;

import javax.servlet.http.HttpServletRequest;

public interface InputCheckingService {

    boolean checkRequestingTest(HttpServletRequest req);

    boolean checkLoginForm(HttpServletRequest req);

    boolean checkRegistrationForm(HttpServletRequest req);
}
