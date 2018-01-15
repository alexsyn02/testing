package ua.com.testing.entity.test;

public class Subject {

    private int id;
    private String description;

    public Subject() {
    }

    public Subject(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Builder {

        private int id;
        private String description;

        public Builder buildId(int id) {
            this.id = id;
            return this;
        }

        public Builder buildDescription(String description) {
            this.description = description;
            return this;
        }

        public Subject build() {
            return new Subject(this);
        }
    }
}
