package ua.com.testing.util;

public class UtilConstants {

    //pages url:
    public static final String EXTENSION = ".jsp";
    public static final String INDEX_PAGE = "/index" + EXTENSION;
    public static final String REGISTRATION_PAGE = "/registration" + EXTENSION;
    public static final String STUDENT_PAGE = "/student-page" + EXTENSION;
    public static final String PASS_TEST_PAGE = "/pass-test" + EXTENSION;
    public static final String TUTOR_TESTS_PAGE = "/tutor-tests" + EXTENSION;
    public static final String CHECK_TEST_PAGE = "/check-test" + EXTENSION;
    public static final String STUDENT_TESTS_PAGE = "/student-tests" + EXTENSION;
    public static final String PAGE_404 = "/page404" + EXTENSION;

    //controller keys:
    public static final String LOG_IN_CONTROLLER_KEY = "logInControllerKey";
    public static final String LOG_OUT_CONTROLLER_KEY = "logOutControllerKey";
    public static final String REGISTRATION_CONTROLLER_KEY = "registrationControllerKey";
    public static final String TEST_FOR_STUDENT_CONTROLLER_KEY = "testForStudentControllerKey";
    public static final String PASS_TEST_CONTROLLER_KEY = "passTestControllerKey";
    public static final String REQUEST_TEST_FORM_CONTROLLER_KEY = "requestTestFormControllerKey";
    public static final String TEST_FOR_TUTOR_CONTROLLER_KEY = "testForTutorControllerKey";
    public static final String CHECK_TEST_CONTROLLER_KEY = "checkTestControllerKey";

    //servlet url patterns:
    public static final String MAIN_SERVLET = "/main-servlet";

    //session attributes names:
    public static final String CURRENT_USER = "currentUser";
    public static final String LIST_OF_QUESTIONS = "listOfQuestions";
    public static final String LIST_OF_SUBJECTS = "listOfSubjects";
    public static final String REQUESTED_SUBJECT_ID = "requestedSubjectId";
    public static final String LIST_OF_TESTS = "listOfTests";
    public static final String LIST_OF_TESTS_BY_TUTOR = "listOfTestsByTutor";
    public static final String TEST_ID = "testId";
    public static final String ID_QUESTION = "idQuestion";
    public static final String LIST_OF_GIVEN_ANSWERS = "listOfGivenAnswers";
    public static final String CURRENT_LOCALE = "currentLocale";

    //request attributes names:
    public static final String CONTROLLER_KEY = "controllerKey";
    public static final String ENTERED_EMAIL = "enteredEmail";
    public static final String REQUEST_LOCALE_PARAM = "requestLocaleParam";
    public static final String LOGIN_ERROR = "loginError";
    public static final String LOGIN_FORM_WRONG_INPUT = "loginFormWrongInput";
    public static final String WRONG_CHOICE = "wrongChoice";
    public static final String WRONG_DATE = "wrongDate";
    public static final String LIST_OF_QUESTIONS_BY_SUBJECT = "listOfQuestionsBySubject";
    public static final String GETTING_TEST_FORM_WRONG_INPUT = "wrongInput";
    public static final String SUCCESSFUL_SENDING_QUESTS = "successfulSendingQuests";
    public static final String SUCCESSFUL_TEST_DECLINING = "successfulTestDeclining";
    public static final String GETTING_TEST_SUCCESS = "gettingTestSuccess";
    public static final String SUCCESSFUL_PASSING_TEST = "successfulPassingTest";
    public static final String SUCCESSFUL_SETTING_MARK = "successfulSettingMark";
    public static final String REGISTRATION_ERROR = "registrationError";
    public static final String REGISTRATION_FORM_WRONG_INPUT = "registrationFormWrongInput";
    public static final String CURRENT_PAGE_NUMBER = "currentPageNumber";
    public static final String SET_DECLINED_STATUS = "setDeclinedStatus";
    public static final String SET_EXPIRED_DATE = "setExpiredDate";
    public static final String TEST_STATUS = "testStatus";
    public static final String PASSED_STATUS = "passedStatus";
    public static final String NOT_PASSED_STATUS = "notPassedStatus";
    public static final String DECLINED_STATUS = "declinedStatus";
    public static final String STATUS_PASSED = "statusPassed";
    public static final String MARK = "mark";
    public static final String CHOSEN_QUESTIONS = "chosenQuestions";

    //registration form parameters names:
    public static final String REG_FIRST_NAME = "reg-first-name";
    public static final String REG_SURNAME = "reg-surname";
    public static final String REG_EMAIL = "reg-email";
    public static final String REG_MOBILE_PHONE = "reg-mobile-phone";
    public static final String REG_PASSWORD = "reg-password";
    public static final String REG_CONFIRM_PASSWORD = "reg-confirm-password";

    //login form parameters names:
    public static final String LOGIN_EMAIL = "login-email";
    public static final String LOGIN_PASSWORD = "login-password";

    //event messages:
    public static final String REQUEST_TEST_EVENT_WRONG_INPUT = "Request a test event: wrong choice. Student id ";
    public static final String REQUEST_TEST_EVENT_SUCCESS = "Request a test event: success. Student id ";
    public static final String LIST_OF_TESTS_UPDATE_EVENT = "List of tests update event: success. User id ";
    public static final String LOGIN_EVENT_ERROR = "Log in event: wrong email or/and password";
    public static final String LOGIN_EVENT_WRONG_INPUT = "Log in event: wrong input";
    public static final String LOGIN_EVENT_SUCCESS = "Log in event: success. User id ";
    public static final String LOG_OUT_EVENT = "Log out event: success";
    public static final String REGISTRATION_EVENT_ERROR = "Registration event: such email already exists";
    public static final String REGISTRATION_EVENT_WRONG_INPUT = "Registration event: wrong input";
    public static final String REGISTRATION_EVENT_SUCCESS = "Registration event: success. User id ";
    public static final String WRONG_CHOICE_MARK_EVENT = "Mark event: wrong. Test id ";
    public static final String SUCCESSFUL_SETTING_MARK_EVENT = "Mark event: success. Test id ";
    public static final String SUCCESSFUL_SENDING_QUESTIONS_EVENT = "Send questions event: success. Test id ";
    public static final String WRONG_DATE_EVENT = "Date event: wrong. Test id ";
    public static final String WRONG_TEST_CHOICE_EVENT = "Test choice event: wrong. User id ";
    public static final String SUCCESSFUL_CHOOSING_TEST_EVENT = "Test choice event: success. Test id ";
    public static final String SUCCESSFUL_DECLINING_TEST_EVENT = "Decline test event: success. Test id ";

    //type of commands:
    public static final String ACCEPT_COMMAND = "ACCEPT";
    public static final String DECLINE_COMMAND = "DECLINE";
    public static final String CONFIRM_COMMAND = "CONFIRM";

    //resource bundle file name:
    public static final String MESSAGES_PROPERTIES = "properties";
}
