package ua.com.testing.service;

import ua.com.testing.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface UserService {

    User findByEmail(String email) throws SQLException;

    int save (User user) throws SQLException;

    boolean verifyTutor(User user);

    User createUserFromRequest(HttpServletRequest request);
}
