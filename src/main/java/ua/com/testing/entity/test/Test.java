package ua.com.testing.entity.test;

import ua.com.testing.entity.user.User;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Test {

    private int id;
    private final Subject subject;
    private final List<Question> questions;
    private final User student;
    private final User tutor;
    private Timestamp beginningDate;
    private Timestamp expiredDate;
    private TestStatus testStatus;
    private int mark;

    private Test(Builder builder) {
        id = builder.id;
        subject = builder.subject;
        questions = builder.questions;
        student = builder.student;
        tutor = builder.tutor;
        beginningDate = builder.beginningDate;
        expiredDate = builder.expiredDate;
        testStatus = builder.testStatus;
        mark = builder.mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public User getStudent() {
        return student;
    }

    public User getTutor() {
        return tutor;
    }

    public Timestamp getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(Timestamp beginningDate) {
        this.beginningDate = beginningDate;
    }

    public Timestamp getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Timestamp expiredDate) {
        this.expiredDate = expiredDate;
    }

    public TestStatus getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(TestStatus testStatus) {
        this.testStatus = testStatus;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public static class Builder {
        private int id;
        private Subject subject;
        private List<Question> questions;
        private User student;
        private User tutor;
        private Timestamp beginningDate;
        private Timestamp expiredDate;
        private TestStatus testStatus;
        private int mark;

        public Builder buildId(int id) {
            this.id = id;
            return this;
        }

        public Builder buildSubject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public Builder buildQuestions(List<Question> questions) {
            this.questions = questions;
            return this;
        }

        public Builder buildStudent(User student) {
            this.student = student;
            return this;
        }

        public Builder buildTutor(User tutor) {
            this.tutor = tutor;
            return this;
        }

        public Builder buildBeginningDate(Timestamp beginningDate) {
            this.beginningDate = beginningDate;
            return this;
        }

        public Builder buildExpiredDate(Timestamp expiredDate) {
            this.expiredDate = expiredDate;
            return this;
        }

        public Builder buildTestStatus(TestStatus testStatus) {
            this.testStatus = testStatus;
            return this;
        }

        public Builder buildMark(int mark) {
            this.mark = mark;
            return this;
        }

        public Test build() {
            return new Test(this);
        }
    }
}
