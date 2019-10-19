package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.api.models.SignUpRequestModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/*
Eventually send info to server
Things to add
-add
-check for possible explicit username
-assign uuid to user randomly
-if someone enters both a wrong name and an invalid password possibility
 */

public class SignUpActivity extends InsiderActivity {

    private boolean professorValidate;
    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        CheckBox prof = findViewById(R.id.checkbox_prof);
        prof.setOnClickListener(this::onCheckboxClicked);

        Button backButton = findViewById(R.id.back_to_login);
        backButton.setOnClickListener(view -> finish());
        findViewById(R.id.sign_in_new_user).setOnClickListener(this::onSignUpClicked);
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            professorValidate = true;
        }
        else {
            professorValidate = false;
        }
    }

    public void onSignUpClicked(View view) {
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

        // checks if checkbox is checked
//        if (professorValidate) {
//            // send verification email
//            return;
//        }

        SignUpRequestModel signUpRequestModel = new SignUpRequestModel();
        signUpRequestModel.firstName = firstName;
        signUpRequestModel.lastName = lastName;
        signUpRequestModel.username = userNameText;
        signUpRequestModel.password = password;

        subscribe = getInsiderApplication()
                .getApiService()
                .signUp(signUpRequestModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(signUpRequestModelResponse -> {
                    if (signUpRequestModelResponse.isSuccessful()) {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                    else {
                        userError.setText("You are already a user!");
                        userError.setVisibility(View.VISIBLE);
                    }
                });

    }

    @Override
    protected void onDestroy() {
        if(subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }

    /**
     * checks to make sure only one word is entered but checking for a space
     *
     * @param userEntry
     * @return true if name is valid
     */
    public boolean checkName(String userEntry) {
        for (int i = 0; i < userEntry.length(); i++) {
            if (userEntry.charAt(i) == ' ') {
                return false;
            }
        }
        return true;
    }

    // username

    /**
     * Password
     * -must contain uppercase
     * -must contain a number
     * -must be equal to or more than 8 characters
     * -returns true if valid password
     **/
    public boolean validPassword(String userPassword) {
        boolean hasUppercase = !userPassword.equals(userPassword.toLowerCase());
        boolean correctLength = userPassword.length() >= 8;
        boolean containsNumber = false;

        for (int i = 0; i < userPassword.length(); i++) {
            containsNumber = Character.isDigit(userPassword.charAt(i));
        }

        return hasUppercase && correctLength && containsNumber;
    }

}
