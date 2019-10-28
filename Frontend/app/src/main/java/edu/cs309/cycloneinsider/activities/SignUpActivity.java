package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import edu.cs309.cycloneinsider.api.Session;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.SignUpRequestModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.SignUpViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/*
Eventually send info to server
Things to add
-add
-check for possible explicit username
-assign uuid to user randomly
-if someone enters both a wrong name and an invalid password possibility
 */

public class SignUpActivity extends InsiderActivity {

    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    Session session;
    private SignUpViewModel signUpViewModel;
    private boolean professorValidate;
    private EditText firstNameText, lastNameText, usernameText, passwordTextOne, passwordTextTwo;
    private TextView userError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstNameText = findViewById(R.id.first_name);
        lastNameText = findViewById(R.id.last_name);
        usernameText = findViewById(R.id.username);
        passwordTextOne = findViewById(R.id.password);
        passwordTextTwo = findViewById(R.id.password_valid);
        userError = findViewById(R.id.user_error);
        userError.setVisibility(View.GONE);

        CheckBox prof = findViewById(R.id.checkbox_prof);
        prof.setOnClickListener(this::onCheckboxClicked);

        ImageButton backButton = findViewById(R.id.back_to_login);
        backButton.setOnClickListener(view -> finish());
        findViewById(R.id.sign_in_new_user).setOnClickListener(this::onSignUpClicked);
    }


    public void onCheckboxClicked(View view) {
        professorValidate = ((CheckBox) view).isChecked();
    }

    public void onSignUpClicked(View view) {

        signUpViewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel.class);

        signUpViewModel.signUp().observe(this, signUpResponseModel -> {
            if (signUpResponseModel.isError()) {
                userError.setVisibility(View.VISIBLE);
                userError.setText(signUpResponseModel.getStringError());
            }
            else {
                String firstName = firstNameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String userNameText = usernameText.getText().toString();
                String password = passwordTextOne.getText().toString();
                signUpViewModel.signUp(new SignUpRequestModel(firstName, lastName, userNameText, password));
                finish();
            }
        });

//        String passwordValid = passwordTextTwo.getText().toString();

//
//        // Checks to make sure only one name is entered
//        if (!checkName(firstName)) {
//            userError.setText("You must only enter your first name");
//            userError.setVisibility(View.VISIBLE);
//            return;
//        }
//        // Checks to make sure only one name is entered
//        else if (!checkName(lastName)) {
//            userError.setText("You must only enter your last name");
//            userError.setVisibility(View.VISIBLE);
//            return;
//        }
//        // checks to make sure that the password meets the correct criteria
//        else if (!validPassword(password)) {
//            userError.setText("You password must contain one uppercase letter, be 8 or more characters, and contain a number");
//            userError.setVisibility(View.VISIBLE);
//            return;
//        }
//        // password to see if both passwords are equal to each other
//        else if (!(password.equals(passwordValid))) {
//            userError.setText("Your passwords must match");
//            userError.setVisibility(View.VISIBLE);
//            return;
//        }
//
//        // checks if checkbox is checked
////        if (professorValidate) {
////            // send verification email
////            return;
////        }
//
//        SignUpRequestModel signUpRequestModel = new SignUpRequestModel();
//        signUpRequestModel.firstName = firstName;
//        signUpRequestModel.lastName = lastName;
//        signUpRequestModel.username = userNameText;
//        signUpRequestModel.password = password;
//
//        subscribe = signUp(getInsiderApplication().getApiService(), signUpRequestModel)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(signUpRequestModelResponse -> {
//                    if (signUpRequestModelResponse.isSuccessful()) {
//                        finish();
//                    } else {
//                        userError.setText("You are already a user!");
//                        userError.setVisibility(View.VISIBLE);
//                    }
//                });

    }

    public Observable<Response<Void>> signUp(CycloneInsiderService service, SignUpRequestModel model) {
        return service.signUp(model);
    }

}
