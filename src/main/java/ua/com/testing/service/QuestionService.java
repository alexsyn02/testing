package ua.com.testing.service;

import ua.com.testing.entity.test.Question;

import java.sql.SQLException;
import java.util.List;

public interface QuestionService {

    static void shuffleAnswers(List<Question> questions) {
        for (Question question : questions) {
            AnswerService.shuffleAnswers(question.getAnswer());
        }
    }

    static void shuffleAnswers(Question question) {
        AnswerService.shuffleAnswers(question.getAnswer());
    }

    List<Question> getQuestionsByTestId(int id) throws SQLException;

    int amountOfQuestionsByTestId(int idTest) throws SQLException;

    List<Question> getQuestionsBySubjectId(int id) throws SQLException;
}
