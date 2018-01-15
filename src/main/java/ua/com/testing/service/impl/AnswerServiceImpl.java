package ua.com.testing.service.impl;

import ua.com.testing.dao.AnswerDao;
import ua.com.testing.dao.GivenAnswerDao;
import ua.com.testing.dao.impl.jdbc.JdbcAnswerDao;
import ua.com.testing.dao.impl.jdbc.JdbcGivenAnswerDao;
import ua.com.testing.entity.test.GivenAnswer;
import ua.com.testing.service.AnswerService;

import java.sql.SQLException;
import java.util.List;

public class AnswerServiceImpl implements AnswerService {

    private final GivenAnswerDao givenAnswerDao;

    public AnswerServiceImpl() {
        givenAnswerDao = new JdbcGivenAnswerDao();
    }

    @Override
    public List<GivenAnswer> getGivenAnswersByTestId(int idTest) throws SQLException {
        return givenAnswerDao.getGivenAnswersByTestId(idTest);
    }

    @Override
    public void updateGivenAnswer(String givenAnswer, int idTest, int idQuestion) throws SQLException {
        givenAnswerDao.update(givenAnswer, idTest, idQuestion);
    }
}
