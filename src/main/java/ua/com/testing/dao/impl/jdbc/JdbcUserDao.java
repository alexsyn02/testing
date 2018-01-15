package ua.com.testing.dao.impl.jdbc;

import ua.com.testing.connector.DBConnector;
import ua.com.testing.dao.UserDao;
import ua.com.testing.entity.user.User;
import ua.com.testing.entity.user.UserRole;

import java.sql.*;

public class JdbcUserDao implements UserDao {

    @Override
    public User findByEmail(String email) throws SQLException {
        User user = null;
        String sql = new StringBuilder()
                .append("SELECT u.id, u.name, u.surname, ua.email, u.mobile_phone, ua.password, ur.role ")
                .append("FROM user AS u ")
                .append("INNER JOIN user_authentication AS ua ")
                .append("ON u.id = ua.user_id ")
                .append("INNER JOIN user_role AS ur ")
                .append("ON ua.role_id = ur.id ")
                .append("WHERE ua.email = ?")
                .toString();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, email.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
        }
        return user;
    }

    @Override
    public int save(User user) throws SQLException {
        int userId = 0;

        String checkingQuery = new StringBuilder()
                .append("SELECT user_id FROM user_authentication AS u ")
                .append("WHERE email = ?")
                .toString();

        String insertUserQuery = new StringBuilder()
                .append("INSERT INTO user (name, surname, mobile_phone) ")
                .append("VALUES (?, ?, ?)")
                .toString();

        String insertAuthDataQuery = new StringBuilder()
                .append("INSERT INTO user_authentication (user_id, email, password, role_id) ")
                .append("VALUES (?, ?, ?, (SELECT id FROM user_role WHERE role = ?))")
                .toString();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement checkingPreparedStatement = connection.prepareStatement(checkingQuery);
             PreparedStatement savingUserPreparedStatement = connection.prepareStatement(insertUserQuery,
                     Statement.RETURN_GENERATED_KEYS);
             PreparedStatement savingAuthDataPreparedStatement = connection.prepareStatement(insertAuthDataQuery)) {

            String email = user.getEmail();

            connection.setAutoCommit(false);

            checkingPreparedStatement.setString(1, email);

            ResultSet resultSet = checkingPreparedStatement.executeQuery();

            if (!resultSet.next()) {
                savingUserPreparedStatement.setString(1, user.getName());
                savingUserPreparedStatement.setString(2, user.getSurname());
                savingUserPreparedStatement.setString(3, user.getMobilePhoneNumber());

                savingUserPreparedStatement.execute();

                ResultSet tableKeys = savingUserPreparedStatement.getGeneratedKeys();

                if (tableKeys.next()) {
                    userId = tableKeys.getInt(1);
                }

                savingAuthDataPreparedStatement.setInt(1, userId);
                savingAuthDataPreparedStatement.setString(2, email);
                savingAuthDataPreparedStatement.setString(3, user.getHashPassword());
                savingAuthDataPreparedStatement.setString(4, UserRole.STUDENT.toString().toLowerCase());

                savingAuthDataPreparedStatement.execute();
            }

            connection.commit();
        }

        return userId;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .buildId(resultSet.getInt("id"))
                .buildName(resultSet.getString("name"))
                .buildSurname(resultSet.getString("surname"))
                .buildEmail(resultSet.getString("email"))
                .buildMobilePhoneNumber(resultSet.getString("mobile_phone"))
                .buildHashPassword(resultSet.getString("password"))
                .buildUserRole(UserRole.valueOf(resultSet.getString("role").toUpperCase()))
                .build();
    }
}
