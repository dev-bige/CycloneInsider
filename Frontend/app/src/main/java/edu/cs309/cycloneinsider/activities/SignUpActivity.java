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
    private CheckBox checkBoxProf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpViewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel.class);

        firstNameText = findViewById(R.id.first_name);
        lastNameText = findViewById(R.id.last_name);
        usernameText = findViewById(R.id.username);
        passwordTextOne = findViewById(R.id.password);
        passwordTextTwo = findViewById(R.id.password_valid);
        userError = findViewById(R.id.user_error);
        checkBoxProf = findViewById(R.id.checkbox_prof);
        userError.setVisibility(View.GONE);

        ImageButton backButton = findViewById(R.id.back_to_login);
        backButton.setOnClickListener(view -> finish());
        findViewById(R.id.sign_in_new_user).setOnClickListener(this::onSignUpClicked);

        signUpViewModel.getSignUpResponse().observe(this, signUpResponseModel -> {
            if (signUpResponseModel.isError()) {
                userError.setVisibility(View.VISIBLE);
                userError.setText(signUpResponseModel.getStringError());
            } else {

                finish();
            }
        });
    }

    /**
     * When the sign button is pressed the information is processed and sends the SignUpRequestModel to
     * the user
     * @param view Passes the current view
     */
    public void onSignUpClicked(View view) {
        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String userNameText = usernameText.getText().toString();
        String password = passwordTextOne.getText().toString();
        boolean isProfessor = checkBoxProf.isChecked();
        signUpViewModel.signUp(new SignUpRequestModel(firstName, lastName, userNameText, password, isProfessor));
    }

    public Observable<Response<Void>> signUp(CycloneInsiderService service, SignUpRequestModel model) {
        return service.signUp(model);
    }

}
