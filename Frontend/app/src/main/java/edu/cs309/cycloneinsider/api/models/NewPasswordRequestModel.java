package edu.cs309.cycloneinsider.api.models;

public class NewPasswordRequestModel {
    public String oldPassword;
    public String newPassword;
    public String newPassword2;

    public NewPasswordRequestModel(String oldPassword, String newPassword, String newPassword2) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPassword2 = newPassword2;
    }

    public NewPasswordRequestModel() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }
}
