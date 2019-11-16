package edu.cs309.cycloneinsider.api.models;

public class SignUpRequestModel {
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public boolean professorValidate;

    public SignUpRequestModel(String firstName, String lastName, String username, String password, boolean professorValidate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.professorValidate = professorValidate;
    }
}
