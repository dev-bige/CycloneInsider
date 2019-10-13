package edu.cs309.cycloneinsider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import edu.cs309.cycloneinsider.R;

public class NewPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
    }

    public void changePassword(View view){

        TextInputEditText oldPassword = findViewById(R.id.old_password);
        String oldPasswordString = oldPassword.getText().toString(); //gets what the user put it for old password
        TextInputEditText newPassword = findViewById(R.id.new_password);
        String newPasswordString = newPassword.getText().toString(); //gets what the user put in for new password
        TextInputEditText newPasswordAgain = findViewById(R.id.new_password_again);
        String newPasswordAgainString = newPasswordAgain.getText().toString(); //gets what the user put in for the second new password
        TextView hiddenMsg = findViewById(R.id.hidden_text_password);
        hiddenMsg.setVisibility(View.INVISIBLE);

        if(oldPasswordString.length() == 0 || newPasswordString.length() == 0 || newPasswordAgainString.length() == 0){
            hiddenMsg.setText("You must enter characters in all text boxes");
            hiddenMsg.setTextColor(Color.parseColor("#FF0000"));
            hiddenMsg.setVisibility(View.VISIBLE);
            return;

        }

        if(!newPasswordString.equals(newPasswordAgainString)){
            hiddenMsg.setText("New password must be the same in both text boxes");
            hiddenMsg.setTextColor(Color.parseColor("#FF0000"));
            hiddenMsg.setVisibility(View.VISIBLE);
            return;

        }

        //TODO need to add changing password functionality
        hiddenMsg.setText("New password has been set");
        hiddenMsg.setTextColor(Color.parseColor("#0000FF"));
        hiddenMsg.setVisibility(View.VISIBLE);


    }
}