package ua.com.testing.entity.test;

import java.util.List;

public class Answer {

    private int idQuestion;
    private List<String> answers;
    private String rightAnswer;

    public Answer() {
    }

    Answer(Builder builder) {
        answers = builder.answers;
        idQuestion = builder.idQuestion;
        rightAnswer = builder.rightAnswer;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public static class Builder {

        private int idQuestion;
        private List<String> answers;
        private String rightAnswer;

        public Builder buildIdQuestion(int idQuestion) {
            this.idQuestion = idQuestion;
            return this;
        }

        public Builder buildAnswers(List<String> answers) {
            this.answers = answers;
            return this;
        }

        public Builder buildRightAnswer(String rightAnswer) {
            this.rightAnswer = rightAnswer;
            return this;
        }

        public Answer build() {
            return new Answer(this);
        }

    }
}
