package ua.com.testing.entity.test;

public class GivenAnswer {

    private int idTest;
    private int idQuestion;
    private String answer;

    public GivenAnswer() {
    }

    public GivenAnswer(Builder builder) {
        idTest = builder.idTest;
        idQuestion = builder.idQuestion;
        answer = builder.answer;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static class Builder {

        private int idTest;
        private int idQuestion;
        private String answer;

        public Builder buildIdTest(int idTest) {
            this.idTest = idTest;
            return this;
        }

        public Builder buildIdQuestion(int idQuestion) {
            this.idQuestion = idQuestion;
            return this;
        }

        public Builder buildAnswer(String answer) {
            this.answer = answer;
            return this;
        }

        public GivenAnswer build() {
            return new GivenAnswer(this);
        }
    }
}
