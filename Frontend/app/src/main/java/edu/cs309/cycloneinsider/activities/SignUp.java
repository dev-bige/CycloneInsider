package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Scanner;

public class SignUp extends AppCompatActivity {

    // protected void OnCreate

    // first name and last name

    // checks to make sure only one word is entered
    public boolean checkName(String userEntry) {

        for (int i = 0; i < userEntry.length(); i++) {
            if (userEntry.charAt(i) == ' ') {
                return false;
            }
        }
        return true;
    }

    // username

    /*
    Password
    -must contain uppercase
    -must contain a number
    -must be equal to or more than 8 characters
     */
    public boolean validPassword(String userPassword) {
        boolean hasUppercase = !userPassword.equals(userPassword.toLowerCase());
        boolean correctLength = userPassword.length() >= 8;
        boolean contiainsNumber = false;

        for (int i = 0; i < userPassword.length(); i++) {
            if (Character.isDigit(userPassword.charAt(i))) {
                contiainsNumber = true;
            }
            else {
                contiainsNumber = false;
            }
        }

        if (hasUppercase && correctLength && contiainsNumber) {
            return true;
        }
        return false;
    }
}
