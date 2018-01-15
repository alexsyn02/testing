package ua.com.testing.entity.test;

public class Question {

    private int id;
    private int idSubject;
    private Answer answer;
    private String description;

    private Question() {
    }

    public Question(Builder builder) {
        id = builder.id;
        idSubject = builder.idSubject;
        answer = builder.answer;
        description = builder.description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Builder {

        private int id;
        private int idSubject;
        private Answer answer;
        private String description;

        public Builder buildId(int id) {
            this.id = id;
            return this;
        }

        public Builder buildIdSubject(int idSubject) {
            this.idSubject = idSubject;
            return this;
        }

        public Builder buildAnswer(Answer answer) {
            this.answer = answer;
            return this;
        }

        public Builder buildDescription(String description) {
            this.description = description;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }
}
