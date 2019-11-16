package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.Session;
import edu.cs309.cycloneinsider.api.models.SignUpRequestModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.SignUpViewModel;
import io.reactivex.Observable;
import retrofit2.Response;


/**
 * Activity that is accessed by pressing the sign up button on the login page
 * The user has the options to either enter the required fields to sign up or press the back
 * button to go to the login page
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
            } else {
                String firstName = firstNameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String userNameText = usernameText.getText().toString();
                String password = passwordTextOne.getText().toString();
                signUpViewModel.signUp(new SignUpRequestModel(firstName, lastName, userNameText, password, professorValidate));
                finish();
            }
        });

    }

    public Observable<Response<Void>> signUp(CycloneInsiderService service, SignUpRequestModel model) {
        return service.signUp(model);
    }

}
