package ua.com.testing.dao;

import ua.com.testing.entity.test.Subject;
import ua.com.testing.entity.test.Test;
import ua.com.testing.entity.user.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface SubjectDao {

    List<Subject> getAllSubjects() throws SQLException;

    Subject findSubjectByTest(Test test) throws SQLException;
}
