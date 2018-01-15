package ua.com.testing.dao.impl.jdbc;

import ua.com.testing.connector.DBConnector;
import ua.com.testing.dao.TestDao;
import ua.com.testing.entity.test.Question;
import ua.com.testing.entity.test.Subject;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.test.TestStatus;
import ua.com.testing.entity.user.User;
import ua.com.testing.entity.user.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTestDao implements TestDao {

    @Override
    public Test load(int id) throws SQLException {

        Test test = null;

        String sql = new StringBuilder()
                .append("SELECT student.id, student.name, student.surname, student.mobile_phone, uas.email, role_student.role, ")
                .append("tutor.id, tutor.name, tutor.surname, tutor.mobile_phone, uat.email, role_tutor.role, ")
                .append("test.id, beginning_date, expired_date, test_status, mark, subject.id, subject ")
                .append("FROM test ")
                .append("JOIN user AS tutor ")
                .append("ON id_tutor = tutor.id ")
                .append("JOIN user AS student ")
                .append("ON id_student = student.id ")
                .append("JOIN subject ")
                .append("ON id_subject = subject.id ")
                .append("JOIN user_authentication AS uat ")
                .append("ON uat.user_id = tutor.id ")
                .append("JOIN user_authentication AS uas ")
                .append("ON uas.user_id = student.id ")
                .append("JOIN user_role AS role_tutor ")
                .append("ON uat.role_id = role_tutor.id ")
                .append("JOIN user_role AS role_student ")
                .append("ON uas.role_id = role_student.id ")
                .append("WHERE test.id = ? ")
                .toString();

        try (Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultSet.first();
                test = buildTest(resultSet);
            }
        }

        return test;
    }

    @Override
    public void decline(int id) throws SQLException {

        String sql = new StringBuilder()
                .append("UPDATE test ")
                .append("SET test_status = 'declined' ")
                .append("WHERE id = ? ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Test> getTestsByUser(User user) throws SQLException {

        List<Test> tests = new ArrayList<>();

        StringBuilder sql = new StringBuilder()
                .append("SELECT student.id, student.name, student.surname, student.mobile_phone, uas.email, role_student.role, ")
                .append("tutor.id, tutor.name, tutor.surname, tutor.mobile_phone, uat.email, role_tutor.role, ")
                .append("test.id, beginning_date, expired_date, test_status, mark, subject.id, subject ")
                .append("FROM test ")
                .append("JOIN user AS tutor ")
                .append("ON id_tutor = tutor.id ")
                .append("JOIN user AS student ")
                .append("ON id_student = student.id ")
                .append("JOIN subject ")
                .append("ON id_subject = subject.id ")
                .append("JOIN user_authentication AS uat ")
                .append("ON uat.user_id = tutor.id ")
                .append("JOIN user_authentication AS uas ")
                .append("ON uas.user_id = student.id ")
                .append("JOIN user_role AS role_tutor ")
                .append("ON uat.role_id = role_tutor.id ")
                .append("JOIN user_role AS role_student ")
                .append("ON uas.role_id = role_student.id ");

        if (user.getUserRole().equals(UserRole.TUTOR)) {
            sql.append("WHERE tutor.id = ? ");
        } else {
            sql.append("WHERE student.id = ? ");
        }

        sql.append("ORDER BY beginning_date ");

        try (Connection connection = DBConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            preparedStatement.setInt(1, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                tests.add(buildTest(resultSet));
            }
        }
        return tests;
    }

    @Override
    public int getNumberRows() {
        return 0;
    }

    @Override
    public void createTest(User student, int idSubject) throws SQLException {

        String sql = new StringBuilder()
                .append("INSERT INTO test (id_student, id_tutor, beginning_date, test_status, mark, id_subject) ")
                .append("VALUES (?, (SELECT id_tutor FROM m2m_subjects_tutors WHERE id_subject = ?), ?, ?, ?, ?) ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, student.getId());
            preparedStatement.setInt(2, idSubject);
            preparedStatement.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
            preparedStatement.setString(4, TestStatus.NEW.toString().toLowerCase());
            preparedStatement.setInt(5, -1);
            preparedStatement.setInt(6, idSubject);

            preparedStatement.execute();
        }
    }

    @Override
    public void setMark(int id, int mark) throws SQLException {

        String sql = new StringBuilder()
                .append("UPDATE test ")
                .append("SET mark = ? ")
                .append("WHERE id = ? ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, mark);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();
        }
    }

    @Override
    public void updateTestStatusAsPassed(int id) throws SQLException {

        String sql = new StringBuilder()
                .append("UPDATE test ")
                .append("SET test_status = 'passed' ")
                .append("WHERE id = ? ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        }
    }

    @Override
    public void updateTestStatusAsNotPassed(int id) throws SQLException {

        String sql = new StringBuilder()
                .append("UPDATE test ")
                .append("SET test_status = 'not_passed' ")
                .append("WHERE id = ? ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        }
    }

    @Override
    public void insertQuestionIntoTest(int idTest, int idQuestion) throws SQLException {

        String sql = new StringBuilder()
                .append("INSERT INTO m2m_tests_questions (id_test, id_question) ")
                .append("VALUES (?, ?) ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idTest);
            preparedStatement.setInt(2, idQuestion);

            preparedStatement.execute();
        }
    }

    @Override
    public void setExpiredDate(int idTest, Timestamp t) throws SQLException {
        String sql = new StringBuilder()
                .append("UPDATE test ")
                .append("SET expired_date = ? ")
                .append("WHERE id = ? ")
                .toString();

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setTimestamp(1, t);
            preparedStatement.setInt(2, idTest);

            preparedStatement.execute();
        }
    }

    private Test buildTest(ResultSet resultSet) throws SQLException {

        Subject subject = new Subject.Builder()
                .buildId(resultSet.getInt("subject.id"))
                .buildDescription(resultSet.getString("subject"))
                .build();

        List<Question> questions = new JdbcQuestionDao().getQuestionsByTestId(resultSet.getInt("test.id"));

        User student = new User.Builder()
                .buildId(resultSet.getInt("student.id"))
                .buildName(resultSet.getString("student.name"))
                .buildSurname(resultSet.getString("student.surname"))
                .buildEmail(resultSet.getString("uas.email"))
                .buildMobilePhoneNumber(resultSet.getString("student.mobile_phone"))
                .buildUserRole(UserRole.valueOf(resultSet.getString("role_student.role").toUpperCase()))
                .build();

        User tutor = new User.Builder()
                .buildId(resultSet.getInt("tutor.id"))
                .buildName(resultSet.getString("tutor.name"))
                .buildSurname(resultSet.getString("tutor.surname"))
                .buildEmail(resultSet.getString("uat.email"))
                .buildMobilePhoneNumber(resultSet.getString("tutor.mobile_phone"))
                .buildUserRole(UserRole.valueOf(resultSet.getString("role_tutor.role").toUpperCase()))
                .build();

        Test.Builder testBuilder = new Test.Builder()
                .buildId(resultSet.getInt("test.id"))
                .buildSubject(subject)
                .buildQuestions(questions)
                .buildStudent(student)
                .buildTutor(tutor)
                .buildBeginningDate(resultSet.getTimestamp("beginning_date"))
                .buildTestStatus(TestStatus.valueOf(resultSet.getString("test_status").toUpperCase()));

        if (!(resultSet.getDate("expired_date") == null)) {
            testBuilder.buildExpiredDate(resultSet.getTimestamp("expired_date"));
        }

        if (!(resultSet.getString("mark") == null)) {
            testBuilder.buildMark(resultSet.getInt("mark"));
        }

        return testBuilder.build();
    }
}
