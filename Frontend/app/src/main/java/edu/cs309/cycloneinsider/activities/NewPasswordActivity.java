package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.Session;
import edu.cs309.cycloneinsider.api.models.NewPasswordRequestModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.LoginViewModel;
import edu.cs309.cycloneinsider.viewmodels.NewPasswordViewModel;
import edu.cs309.cycloneinsider.viewmodels.responsemodels.NewPasswordResponseModel;
import io.reactivex.disposables.Disposable;

public class NewPasswordActivity extends InsiderActivity {
    private Disposable subscribe;
    private NewPasswordViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    Session session;


   /* public void changePassword(View view) {

        TextInputEditText oldPassword = findViewById(R.id.old_password);
        String oldPasswordString = oldPassword.getText().toString(); //gets what the user put it for old password
        TextInputEditText newPassword = findViewById(R.id.new_password);
        String newPasswordString = newPassword.getText().toString(); //gets what the user put in for new password
        TextInputEditText newPasswordAgain = findViewById(R.id.new_password_again);
        String newPasswordAgainString = newPasswordAgain.getText().toString(); //gets what the user put in for the second new password
        TextView hiddenMsg = findViewById(R.id.hidden_text_password);
        hiddenMsg.setVisibility(View.INVISIBLE);


        if (oldPasswordString.length() == 0 || newPasswordString.length() == 0 || newPasswordAgainString.length() == 0) {
            hiddenMsg.setText("You must enter characters in all text boxes");
            hiddenMsg.setTextColor(Color.parseColor("#FF0000"));
            hiddenMsg.setVisibility(View.VISIBLE);
            return;

        }

        if (!newPasswordString.equals(newPasswordAgainString)) {
            hiddenMsg.setText("New password must be the same in both text boxes");
            hiddenMsg.setTextColor(Color.parseColor("#FF0000"));
            hiddenMsg.setVisibility(View.VISIBLE);
            return;

        }


        subscribe = getInsiderApplication()
                .getApiService()
                .currentUser().subscribe(response -> {
                    if (response.isSuccessful()) {
                        hiddenMsg.setText("New password has been set");
                        hiddenMsg.setTextColor(Color.parseColor("#0000FF"));
                        hiddenMsg.setVisibility(View.VISIBLE);

                    } else {
                        hiddenMsg.setText("Error, password was not reset");
                        hiddenMsg.setTextColor(Color.parseColor("#FF0000"));
                        hiddenMsg.setVisibility(View.VISIBLE);
                    }
                });

        //    System.out.println(user.getPassword());

    *//*    if(user.getPassword().equals(oldPasswordString)) {
            user.setPassword(newPasswordString);
            hiddenMsg.setText("New password has been set");
            hiddenMsg.setTextColor(Color.parseColor("#0000FF"));
            hiddenMsg.setVisibility(View.VISIBLE);
        }*//*
        return;


    }*/

    /**
     * Method checks if the password can be changed
     * @param old old password
     * @param newPass new password
     * @param newPass2 new password in 2nd text box
     * @return boolean value if the password can be changed or not
     */
    public boolean checkPassword(String old, String newPass, String newPass2) {


        if (old.length() == 0 || newPass.length() == 0 || newPass2.length() == 0) {

            return false;

        }

        if (!newPass.equals(newPass2)) {

            return false;

        }

        return true;

    }

    /**
     * Method creates the layout and then creates an observable for changing the user's password
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        TextInputEditText oldPassword = findViewById(R.id.old_password);
        TextInputEditText newPassword = findViewById(R.id.new_password);
        TextInputEditText newPasswordAgain = findViewById(R.id.new_password_again);
        TextView hiddenMsg = findViewById(R.id.hidden_text_password);
        hiddenMsg.setVisibility(View.INVISIBLE);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewPasswordViewModel.class);
        findViewById(R.id.change_password).setOnClickListener(view -> {
            String oldPasswordString = oldPassword.getText().toString(); //gets what the user put it for old password
            String newPasswordAgainString = newPasswordAgain.getText().toString(); //gets what the user put in for the second new password
            String newPasswordString = newPassword.getText().toString(); //gets what the user put in for new password
            viewModel.changePassword(new NewPasswordRequestModel(oldPasswordString, newPasswordString, newPasswordAgainString));
                }
        );

        viewModel.getNewPasswordResponse().observe(this, newPasswordResponseModel -> {
            if (newPasswordResponseModel.isError()) {
                hiddenMsg.setVisibility(View.VISIBLE);
                hiddenMsg.setText(newPasswordResponseModel.getStringError());
            } else if (newPasswordResponseModel.getRawResponse().isSuccessful()) {
                hiddenMsg.setText("New password has been set");
                hiddenMsg.setTextColor(Color.parseColor("#0000FF"));
                hiddenMsg.setVisibility(View.VISIBLE);
                finish();
            }
        });

    }

    /**
     * Destroys activity
     */
    @Override
    protected void onDestroy() {

        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();

    }

}
