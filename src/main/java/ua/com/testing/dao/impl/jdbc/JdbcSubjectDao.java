package ua.com.testing.dao.impl.jdbc;

import ua.com.testing.connector.DBConnector;
import ua.com.testing.dao.SubjectDao;
import ua.com.testing.entity.test.Subject;
import ua.com.testing.entity.test.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcSubjectDao implements SubjectDao {


    @Override
    public List<Subject> getAllSubjects() throws SQLException {

        List<Subject> subjects = new ArrayList<>();

        String sql = new StringBuilder()
                .append("SELECT id, subject ")
                .append("FROM subject ")
                .append("ORDER BY id ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                subjects.add(buildSubject(resultSet));
            }
        }

        return subjects;
    }

    @Override
    public Subject findSubjectByTest(Test test) throws SQLException {

        Subject subject = null;

        String sql = new StringBuilder()
                .append("SELECT s.id, s.subject ")
                .append("FROM user AS u ")
                .append("JOIN test AS t ")
                .append("ON u.id = t.id_student ")
                .append("JOIN subject AS s ")
                .append("ON s.id = t.id_subject ")
                .append("WHERE s.id = ?").toString();

        try (Connection connection = DBConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, (test.getSubject().getId()));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultSet.first();
                subject = buildSubject(resultSet);
            }
        }

        return subject;
    }

    private Subject buildSubject(ResultSet resultSet) throws SQLException {
        return new Subject.Builder()
                .buildId(resultSet.getInt("subject.id"))
                .buildDescription(resultSet.getString("subject.subject"))
                .build();
    }
}
