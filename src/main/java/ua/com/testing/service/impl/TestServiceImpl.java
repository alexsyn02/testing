package ua.com.testing.service.impl;

import ua.com.testing.dao.impl.jdbc.JdbcTestDao;
import ua.com.testing.dao.TestDao;
import ua.com.testing.entity.test.Question;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.test.TestStatus;
import ua.com.testing.entity.user.User;
import ua.com.testing.service.AnswerService;
import ua.com.testing.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static ua.com.testing.util.UtilConstants.LIST_OF_QUESTIONS;
import static ua.com.testing.util.UtilConstants.TEST_ID;

public class TestServiceImpl implements TestService {

    private final TestDao testDao;
    private final AnswerService answerService;

    public TestServiceImpl() {
        testDao = new JdbcTestDao();
        answerService = new AnswerServiceImpl();
    }

    @Override
    public Test load(int id) throws SQLException {
        return testDao.load(id);
    }

    @Override
    public void decline(int id) throws SQLException {
        testDao.decline(id);
    }

    @Override
    public void processRequestingTest(User student, int idSubject) throws SQLException {
        testDao.createTest(student, idSubject);
    }

    @Override
    public List<Test> getTestsByUser(User user) throws SQLException {
        return testDao.getTestsByUser(user);
    }

    @Override
    public void setMark(int id, int mark) throws SQLException {
        testDao.setMark(id, mark);
    }

    @Override
    public void updateTestStatusAsPassed(int id) throws SQLException {
        testDao.updateTestStatusAsPassed(id);
    }

    @Override
    public void updateTestStatusAsNotPassed(int id) throws SQLException {
        testDao.updateTestStatusAsNotPassed(id);
    }

    @Override
    public boolean checkTestForPassedStatus(Test test) {
        return test.getTestStatus().equals(TestStatus.PASSED);
    }

    @Override
    public void updateTestAnswers(HttpServletRequest req, HttpSession session) throws SQLException {

        List<Question> questions = (List<Question>) session.getAttribute(LIST_OF_QUESTIONS);
        int amountOfQuestions = questions.size();
        int idTest = (int)session.getAttribute(TEST_ID);

        for (int i = 0; i < amountOfQuestions; i++) {

            int currentQuestionId = questions.get(i).getId();
            answerService.updateGivenAnswer((String)req.getParameter(String.valueOf(currentQuestionId)), idTest, currentQuestionId);
            updateTestStatusAsPassed(idTest);
        }
    }

    @Override
    public void insertQuestionIntoTest(int idTest, int idQuestion) throws SQLException {
        testDao.insertQuestionIntoTest(idTest, idQuestion);
    }

    @Override
    public void setExpiredDate(int idTest, Timestamp t) throws SQLException {
        testDao.setExpiredDate(idTest, t);
    }

    @Override
    public void updateTestListIntoSession(HttpServletRequest req) {

    }
}
