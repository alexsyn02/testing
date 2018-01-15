package ua.com.testing.entity.user;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String mobilePhoneNumber;
    private String hashPassword;
    private UserRole userRole;

    public User() {}

    private User(Builder builder) {
        id = builder.id;
        name = builder.name;
        surname = builder.surname;
        email = builder.email;
        mobilePhoneNumber = builder.mobilePhoneNumber;
        hashPassword = builder.hashPassword;
        userRole = builder.userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public static class Builder {
        private int id;
        private String name;
        private String surname;
        private String email;
        private String mobilePhoneNumber;
        private String hashPassword;
        private UserRole userRole;

        public Builder buildId(int id) {
            this.id = id;
            return this;
        }

        public Builder buildName(String name) {
            this.name = name;
            return this;
        }

        public Builder buildSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder buildEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder buildMobilePhoneNumber(String mobilePhoneNumber) {
            this.mobilePhoneNumber = mobilePhoneNumber;
            return this;
        }

        public Builder buildHashPassword(String hashPassword) {
            this.hashPassword = hashPassword;
            return this;
        }

        public Builder buildUserRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
