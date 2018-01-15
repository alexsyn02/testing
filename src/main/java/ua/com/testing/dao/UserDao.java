package ua.com.testing.dao;

import ua.com.testing.entity.user.User;

import java.sql.SQLException;

public interface UserDao {
    User findByEmail(String email) throws SQLException;
    int save(User user) throws SQLException;
}
