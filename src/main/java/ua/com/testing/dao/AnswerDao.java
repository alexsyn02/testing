package ua.com.testing.dao;

import ua.com.testing.entity.test.Answer;
import ua.com.testing.entity.test.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AnswerDao {

    Answer getAnswer(int idQuestion) throws SQLException;

    String getRightAnswerString(int idQuestion) throws SQLException;
}
