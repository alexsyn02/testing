package ua.com.testing.service.impl;

import ua.com.testing.dao.impl.jdbc.JdbcSubjectDao;
import ua.com.testing.dao.SubjectDao;
import ua.com.testing.entity.test.Subject;
import ua.com.testing.entity.test.Test;
import ua.com.testing.service.SubjectService;

import java.sql.SQLException;
import java.util.List;

public class SubjectServiceImpl implements SubjectService {

    private final SubjectDao subjectDao;

    public SubjectServiceImpl() {
        subjectDao = new JdbcSubjectDao();
    }

    @Override
    public List<Subject> getAllSubjects() throws SQLException {
        return subjectDao.getAllSubjects();
    }

    @Override
    public Subject findSubjectByTest(Test test) throws SQLException {
        return subjectDao.findSubjectByTest(test);
    }
}
