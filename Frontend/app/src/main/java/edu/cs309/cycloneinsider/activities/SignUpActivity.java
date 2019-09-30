package edu.cs309.cycloneinsider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.cs309.cycloneinsider.R;

/*
Eventually send info to server
Things to add
-check for possible explicit username
-assign uuid to user randomly
-if someone enters both a wrong name and an invalid password possibility
 */

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button backButton = findViewById(R.id.back_to_login);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        setContentView(R.layout.activity_sign_up);
    }

    public void OpenSignUpPage(View view) {
        EditText firstNameText = findViewById(R.id.first_name);
        EditText lastNameText = findViewById(R.id.last_name);
        EditText usernameText = findViewById(R.id.username);
        EditText passwordTextOne = findViewById(R.id.password);
        EditText passwordTextTwo = findViewById(R.id.password_valid);
        TextView userError = findViewById(R.id.user_error);
        userError.setVisibility(View.GONE);

        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String userNameText = usernameText.getText().toString();
        String password = passwordTextOne.getText().toString();
        String passwordValid = passwordTextTwo.getText().toString();

        // Checks to make sure only one name is entered
        if (!checkName(firstName)) {
            userError.setText("You must only enter your first name");
            userError.setVisibility(View.VISIBLE);
            return;
        }
        // Checks to make sure only one name is entered
        else if (!checkName(lastName)) {
            userError.setText("You must only enter your last name");
            userError.setVisibility(View.VISIBLE);
            return;
        }
        // checks to make sure that the password meets the correct criteria
        else if (!validPassword(password)) {
            userError.setText("You password must contain one uppercase letter, be 8 or more characters, and contain a number");
            userError.setVisibility(View.VISIBLE);
            return;
        }
        // password to see if both passwords are equal to each other
        else if (!(password.equals(passwordValid))) {
            userError.setText("Your passwords must match");
            userError.setVisibility(View.VISIBLE);
            return;
        }


    }

    // checks to make sure only one word is entered but checking for a space
    // returns true if name is valid
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
    -returns true if valid password
     */
    public boolean validPassword(String userPassword) {
        boolean hasUppercase = !userPassword.equals(userPassword.toLowerCase());
        boolean correctLength = userPassword.length() >= 8;
        boolean containsNumber = false;

        for (int i = 0; i < userPassword.length(); i++) {
            if (Character.isDigit(userPassword.charAt(i))) {
                containsNumber = true;
            }
            else {
                containsNumber = false;
            }
        }

        if (hasUppercase && correctLength && containsNumber) {
            return true;
        }
        return false;
    }

}
