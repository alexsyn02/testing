package ua.com.testing.dao;

import ua.com.testing.entity.test.GivenAnswer;

import java.sql.SQLException;
import java.util.List;

public interface GivenAnswerDao {

    public GivenAnswer load(int idTest, int idQuestion) throws SQLException;

    public void update(String givenAnswer, int idTest, int idQuestion) throws SQLException;

    public List<GivenAnswer> getGivenAnswersByTestId(int idTest) throws SQLException;

}
