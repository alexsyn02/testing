package ua.com.testing.service.impl;

import ua.com.testing.dao.impl.jdbc.JdbcQuestionDao;
import ua.com.testing.dao.QuestionDao;
import ua.com.testing.entity.test.Question;
import ua.com.testing.service.QuestionService;

import java.sql.SQLException;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl() {
        questionDao = new JdbcQuestionDao();
    }

    @Override
    public List<Question> getQuestionsByTestId(int id) throws SQLException {
        return questionDao.getQuestionsByTestId(id);
    }

    @Override
    public int amountOfQuestionsByTestId(int idTest) throws SQLException {
        return questionDao.getAmountOfQuestionsByTestId(idTest);
    }

    @Override
    public List<Question> getQuestionsBySubjectId(int id) throws SQLException {
        return questionDao.getQuestionsBySubjectId(id);
    }
}