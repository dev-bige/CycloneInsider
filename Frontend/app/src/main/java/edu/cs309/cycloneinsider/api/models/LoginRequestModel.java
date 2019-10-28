package edu.cs309.cycloneinsider.api.models;

public class LoginRequestModel {
    public String username;
    public String password;

    public LoginRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequestModel() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
