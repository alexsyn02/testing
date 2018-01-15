package ua.com.testing.dao.impl.jdbc;

import ua.com.testing.connector.DBConnector;
import ua.com.testing.dao.GivenAnswerDao;
import ua.com.testing.entity.test.GivenAnswer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcGivenAnswerDao implements GivenAnswerDao {

    @Override
    public GivenAnswer load(int idTest, int idQuestion) throws SQLException {

        GivenAnswer givenAnswer = null;

        String sql = new StringBuilder()
                .append("SELECT id_test, id_question, given_answer ")
                .append("FROM m2m_tests_questions ")
                .append("WHERE id_test = ? AND id_question = ? ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idTest);
            preparedStatement.setInt(2, idQuestion);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultSet.first();
                givenAnswer = buildGivenAnswer(resultSet);
            }
        }

        return givenAnswer;
    }

    @Override
    public void update(String givenAnswer, int idTest, int idQuestion) throws SQLException {

        String sql = new StringBuilder()
                .append("UPDATE m2m_tests_questions ")
                .append("SET given_answer = ? ")
                .append("WHERE id_test = ? AND id_question = ? ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, givenAnswer);
            preparedStatement.setInt(2, idTest);
            preparedStatement.setInt(3, idQuestion);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<GivenAnswer> getGivenAnswersByTestId(int idTest) throws SQLException {

        List<GivenAnswer> givenAnswers = new ArrayList<>();

        String sql = new StringBuilder()
                .append("SELECT id_test, id_question, given_answer ")
                .append("FROM m2m_tests_questions ")
                .append("WHERE id_test = ? ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idTest);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                givenAnswers.add(buildGivenAnswer(resultSet));
            }
        }

        return givenAnswers;
    }

    private GivenAnswer buildGivenAnswer(ResultSet resultSet) throws SQLException {

        GivenAnswer.Builder builder = new GivenAnswer.Builder()
                .buildIdTest(resultSet.getInt("id_test"))
                .buildIdQuestion(resultSet.getInt("id_question"));

        if (!"".equals(resultSet.getString("given_answer"))) {
            builder.buildAnswer(resultSet.getString("given_answer"));
        }

        return builder.build();
    }
}
