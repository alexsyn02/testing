package ua.com.testing.dao;

import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.user.User;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface TestDao {

    Test load(int id) throws SQLException;

    void decline(int id) throws SQLException;

    List<Test> getTestsByUser(User user) throws SQLException;

    int getNumberRows();

    void createTest(User student, int idSubject) throws SQLException;

    void setMark(int id, int mark) throws SQLException;

    void updateTestStatusAsPassed(int id) throws SQLException;

    void updateTestStatusAsNotPassed(int id) throws SQLException;

    void insertQuestionIntoTest(int idTest, int idQuestion) throws SQLException;

    void setExpiredDate(int idTest, Timestamp t) throws SQLException;
}