package ua.com.testing.dao.impl.jdbc;

import ua.com.testing.connector.DBConnector;
import ua.com.testing.dao.QuestionDao;
import ua.com.testing.entity.test.Answer;
import ua.com.testing.entity.test.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcQuestionDao implements QuestionDao {

    @Override
    public List<Question> getQuestionsByTestId(int id) throws SQLException {

        List<Question> questions = new ArrayList<>();

        String sql = new StringBuilder()
                .append("SELECT q.id, q.id_subject, q.question_name, ")
                .append("a.id_question, right_answer, wrong_answer1, wrong_answer2, wrong_answer3 ")
                .append("FROM question AS q ")
                .append("JOIN subject AS s ")
                .append("ON s.id = q.id_subject ")
                .append("JOIN answer AS a ")
                .append("ON q.id = a.id_question ")
                .append("JOIN test AS t ")
                .append("ON t.id_subject = s.id ")
                .append("WHERE t.id = ? ")
                .append("AND (t.id, q.id) in (SELECT id_test, id_question FROM m2m_tests_questions) ")
                .toString();

        try (Connection connection = DBConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                questions.add(buildQuestion(resultSet));
            }
        }

        return questions;
    }

    @Override
    public List<Question> getQuestionsBySubjectId(int id) throws SQLException {

        List<Question> questions = new ArrayList<>();

        String sql = new StringBuilder()
                .append("SELECT q.id, q.id_subject, q.question_name, ")
                .append("a.id_question, right_answer, wrong_answer1, wrong_answer2, wrong_answer3 ")
                .append("FROM question AS q ")
                .append("JOIN subject AS s ")
                .append("ON s.id = q.id_subject ")
                .append("JOIN answer AS a ")
                .append("ON q.id = a.id_question ")
                .append("WHERE s.id = ? ")
                .toString();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questions.add(buildQuestion(resultSet));
            }
        }

        return questions;
    }

    @Override
    public Question getQuestionById(int id) throws SQLException {

        Question question = null;

        String sql = new StringBuilder()
                .append("SELECT id, id_subject, question_name ")
                .append("a.id_question, right_answer, wrong_answer1, wrong_answer2, wrong_answer3 ")
                .append("FROM question ")
                .append("JOIN answer AS a ")
                .append("ON q.id = a.id_question ")
                .append("WHERE q.id = ? ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                question = buildQuestion(resultSet);
            }
        }

        return question;
    }

    @Override
    public int getAmountOfQuestionsByTestId(int id) throws SQLException {

        int result = 0;

        String sql = new StringBuilder()
                .append("SELECT COUNT(q.id) ")
                .append("FROM question AS q ")
                .append("JOIN subject AS s ")
                .append("ON s.id = q.id_subject ")
                .append("JOIN test AS t ")
                .append("ON t.id_subject = s.id ")
                .append("WHERE t.id = ? ")
                .toString();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        }
        return result;
    }

    private Question buildQuestion(ResultSet resultSet) throws SQLException {

        List<String> answers = new ArrayList<>();

        answers.add(resultSet.getString("right_answer"));
        answers.add(resultSet.getString("wrong_answer1"));
        answers.add(resultSet.getString("wrong_answer2"));
        answers.add(resultSet.getString("wrong_answer3"));

        Answer answer = new Answer.Builder()
                .buildIdQuestion(resultSet.getInt("q.id"))
                .buildAnswers(answers)
                .buildRightAnswer(resultSet.getString("right_answer"))
                .build();

        return new Question.Builder()
                .buildId(resultSet.getInt("q.id"))
                .buildIdSubject(resultSet.getInt("q.id_subject"))
                .buildAnswer(answer)
                .buildDescription(resultSet.getString("question_name"))
                .build();
    }
}
