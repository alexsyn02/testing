package ua.com.testing.dao.impl.jdbc;

import ua.com.testing.connector.DBConnector;
import ua.com.testing.dao.AnswerDao;
import ua.com.testing.entity.test.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcAnswerDao implements AnswerDao {

    @Override
    public Answer getAnswer(int idQuestion) throws SQLException {

        Answer answer = null;

        String sql = new StringBuilder()
                .append("SELECT id_question, right_answer, wrong_answer1, wrong_answer2, wrong_answer3 ")
                .append("FROM answer ")
                .append("WHERE id_question = ?").toString();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idQuestion);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultSet.first();
                answer = buildAnswer(resultSet);
            }
        }

        return answer;
    }

    @Override
    public String getRightAnswerString(int idQuestion) throws SQLException {

        String result = null;

        String sql = new StringBuilder()
                .append("SELECT right_answer ")
                .append("FROM answer ")
                .append("WHERE id_question = ?").toString();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idQuestion);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultSet.first();
                result = resultSet.getString("right_answer");
            }
        }

        return result;
    }

    private Answer buildAnswer(ResultSet resultSet) throws SQLException {

        List<String> answers = new ArrayList<>();

        answers.add(resultSet.getString("right_answer"));
        answers.add(resultSet.getString("wrong_answer1"));
        answers.add(resultSet.getString("wrong_answer2"));
        answers.add(resultSet.getString("wrong_answer3"));

        return new Answer.Builder()
                .buildIdQuestion(resultSet.getInt("id_question"))
                .buildAnswers(answers)
                .buildRightAnswer(resultSet.getString("right_answer"))
                .build();
    }
}
