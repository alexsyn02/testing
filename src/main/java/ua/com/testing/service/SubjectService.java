package ua.com.testing.service;

import ua.com.testing.entity.test.Subject;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.user.User;

import java.sql.SQLException;
import java.util.List;

public interface SubjectService {

    List<Subject> getAllSubjects() throws SQLException;

    Subject findSubjectByTest(Test test) throws SQLException;
}
