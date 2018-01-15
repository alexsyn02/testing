package ua.com.testing.util;

import ua.com.testing.controller.*;
import ua.com.testing.controller.impl.*;

import java.util.*;

import static ua.com.testing.util.UtilConstants.*;

public class UtilData {
    public static final Map<String, Controller> CONTROLLERS_MAP;
    public static final Set<String> GUEST_URL_SET;
    public static final Set<String> TUTOR_URL_SET;
    public static final Set<String> STUDENT_URL_SET;

    public static final String NAME_REGEX = "[A-Za-zЄ-ЯҐа-їґ]{2,50}";
    public static final String PHONE_NUMBER_REGEX = "^[0-9\\-\\+]{9,15}";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}" +
            "[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*";
    public static final String PASSWORD_REGEX = "[A-Za-z0-9~!@#$%^&*()-_=+/|.]{6,50}";

    static {
        CONTROLLERS_MAP = new HashMap<>();
        CONTROLLERS_MAP.put(LOG_IN_CONTROLLER_KEY, new LoginController());
        CONTROLLERS_MAP.put(LOG_OUT_CONTROLLER_KEY, new LogoutController());
        CONTROLLERS_MAP.put(REGISTRATION_CONTROLLER_KEY, new RegistrationController());
        CONTROLLERS_MAP.put(TEST_FOR_STUDENT_CONTROLLER_KEY, new TestForStudentController());
        CONTROLLERS_MAP.put(REQUEST_TEST_FORM_CONTROLLER_KEY, new RequestTestFormController());
        CONTROLLERS_MAP.put(PASS_TEST_CONTROLLER_KEY, new PassTestController());
        CONTROLLERS_MAP.put(TEST_FOR_TUTOR_CONTROLLER_KEY, new TestForTutorController());
        CONTROLLERS_MAP.put(CHECK_TEST_CONTROLLER_KEY, new CheckTestController());

        GUEST_URL_SET = Collections.unmodifiableSet(
                new HashSet<>(Arrays.asList(INDEX_PAGE, REGISTRATION_PAGE)));
        STUDENT_URL_SET = Collections.unmodifiableSet(
                new HashSet<>(Arrays.asList(STUDENT_PAGE, STUDENT_TESTS_PAGE, PASS_TEST_PAGE)));
        TUTOR_URL_SET = Collections.unmodifiableSet(
                new HashSet<>(Arrays.asList(TUTOR_TESTS_PAGE, CHECK_TEST_PAGE)));
    }
}
