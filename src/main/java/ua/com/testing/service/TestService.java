package ua.com.testing.service;

import ua.com.testing.entity.test.Subject;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface TestService {

    Test load(int id) throws SQLException;

    void decline(int id) throws SQLException;

    List<Test> getTestsByUser(User user) throws SQLException;

    void processRequestingTest(User student, int idSubject) throws SQLException;

    void setMark(int id, int mark) throws SQLException;

    void updateTestStatusAsPassed(int id) throws SQLException;

    void updateTestStatusAsNotPassed(int id) throws SQLException;

    boolean checkTestForPassedStatus(Test test);

    void updateTestAnswers(HttpServletRequest req, HttpSession session) throws SQLException;

    void insertQuestionIntoTest(int idTest, int idQuestion) throws SQLException;

    void setExpiredDate(int idTest, Timestamp t) throws SQLException;

    void updateTestListIntoSession(HttpServletRequest req);
}
