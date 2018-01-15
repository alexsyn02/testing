package ua.com.testing.service;

import ua.com.testing.entity.test.Answer;
import ua.com.testing.entity.test.GivenAnswer;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public interface AnswerService {

    static void shuffleAnswers(Answer answer) {
        Collections.shuffle(answer.getAnswers());
    }

    List<GivenAnswer> getGivenAnswersByTestId (int idTest) throws SQLException;

    void updateGivenAnswer(String givenAnswer, int idTest, int idQuestion) throws SQLException;
}