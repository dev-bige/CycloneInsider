package edu.cs309.cycloneinsider.activities;

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
import edu.cs309.cycloneinsider.viewmodels.NewPasswordViewModel;
import io.reactivex.disposables.Disposable;

public class NewPasswordActivity extends InsiderActivity {
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    Session session;
    private Disposable subscribe;
    private NewPasswordViewModel viewModel;

    /**
     * Method checks if the password can be changed
     *
     * @param old      old password
     * @param newPass  new password
     * @param newPass2 new password in 2nd text box
     * @return boolean value if the password can be changed or not
     */
    public boolean checkPassword(String old, String newPass, String newPass2) {

        if (old.length() == 0 || newPass.length() == 0 || newPass2.length() == 0) {
            return false;
        }
        return newPass.equals(newPass2);
    }

    /**
     * Method creates the layout and then creates an observable for changing the user's password
     *
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
