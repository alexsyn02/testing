package ua.com.testing.dao;

import ua.com.testing.entity.test.Question;
import ua.com.testing.entity.test.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface QuestionDao {
    List<Question> getQuestionsByTestId(int id) throws SQLException;
    List<Question> getQuestionsBySubjectId(int id) throws SQLException;
    Question getQuestionById(int id) throws SQLException;
    int getAmountOfQuestionsByTestId(int idTest) throws SQLException;
}
