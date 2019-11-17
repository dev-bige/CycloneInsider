package edu.cyclone.insider.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class InsiderUser extends BaseModel {
    /**
     * Model for generating a user
     */
    private String username;
    @JsonIgnore
    private String password;

    private String firstName;
    private String lastName;
    private Boolean pending;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private UserLevel userLevel = UserLevel.USER;

    public InsiderUser() {
    }

    /**
     * gets the username of the current user
     *
     * @return username- of type String
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username to that of the current user
     *
     * @param username - of Type String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the password of the user
     *
     * @return password -of type String
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the password to that of the current password the user created
     *
     * @param password- of type String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets the first name of the current user
     *
     * @return firstName -of String type
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the first name to that of the current user
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the last name of the current user
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the last name of the user to that of current user
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets the full name the user signed up with
     *
     * @return the full name of the user
     */
    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    /**
     * gets to see if current user is a Admin
     *
     * @return Userlevel
     */
    public Boolean getAdmin() {
        return this.userLevel == UserLevel.ADMIN;
    }

    /**
     * sets the userlevel to Admin (permissions)
     *
     * @param isAdmin
     */
    public void setAdmin(Boolean isAdmin) {
        this.userLevel = UserLevel.ADMIN;
    }

    /**
     * gets the current user level of the user and checks is they have Prof permissions
     *
     * @return userLevel
     */
    public Boolean getProfessor() {
        return this.userLevel == UserLevel.PROFESSOR;
    }

    public Boolean getProfPending() {
        return pending;
    }

    /**
     * sets the user to pending when Box on front end is checked.
     */

    public void setProfPending(Boolean pending) {
        this.pending = pending;
    }

    /**
     * gets the permission level a user has (i.e Admin,Professor,User)
     *
     * @return the level of the user
     */
    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }
}
