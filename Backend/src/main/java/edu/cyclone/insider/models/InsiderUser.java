package edu.cyclone.insider.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class InsiderUser extends BaseModel {
    private String username;
    @JsonIgnore
    private String password;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column
    private UserLevel userLevel = UserLevel.USER;

    public InsiderUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAdmin(Boolean isAdmin) {
        this.userLevel = UserLevel.ADMIN;
    }

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    public Boolean getAdmin() {
        return this.userLevel == UserLevel.ADMIN;
    }

    public Boolean getProfessor() {
        return this.userLevel == UserLevel.PROFESSOR;
    }

    public UserLevel getUserLevel() {
        return userLevel;
    }
}
