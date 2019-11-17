package edu.cs309.cycloneinsider.api.models;

public class SignUpRequestModel {
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public Boolean isProfessor;

    public SignUpRequestModel(String firstName, String lastName, String username, String password, Boolean isProfessor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isProfessor = isProfessor;
    }
}
