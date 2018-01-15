package ua.com.testing.service.impl;

import ua.com.testing.dao.impl.jdbc.JdbcUserDao;
import ua.com.testing.dao.UserDao;
import ua.com.testing.entity.user.User;
import ua.com.testing.entity.user.UserRole;
import ua.com.testing.service.PasswordHashingService;
import ua.com.testing.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static ua.com.testing.util.UtilConstants.*;
import static ua.com.testing.util.UtilConstants.REG_PASSWORD;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
        userDao = new JdbcUserDao();
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        return userDao.findByEmail(email);
    }

    @Override
    public int save(User user) throws SQLException {
        return userDao.save(user);
    }

    @Override
    public boolean verifyTutor(User user) {
        return user.getUserRole().equals(UserRole.TUTOR);
    }

    @Override
    public User createUserFromRequest(HttpServletRequest request) {
        String name = request.getParameter(REG_FIRST_NAME);
        String surname = request.getParameter(REG_SURNAME);
        String email = request.getParameter(REG_EMAIL).toLowerCase();
        String mobilePhone = request.getParameter(REG_MOBILE_PHONE);
        String password = request.getParameter(REG_PASSWORD);
        String hashPassword = PasswordHashingService.hashPassword(password);

        return new User.Builder()
                .buildName(name)
                .buildSurname(surname)
                .buildEmail(email)
                .buildMobilePhoneNumber(mobilePhone)
                .buildHashPassword(hashPassword)
                .build();
    }
}
