package edu.cs309.cycloneinsider;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.junit.Test;

import edu.cs309.cycloneinsider.CycloneInsiderApp;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.InsiderActivity;
import edu.cs309.cycloneinsider.activities.NewPasswordActivity;
import edu.cs309.cycloneinsider.activities.SettingsActivity;

//import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class TestSettingsActivity extends {
    private NewPasswordActivity test;

  //  @Mock
    CycloneInsiderApp mock;


    @Test
    public void passwordValidator_ReturnsTrue() {
        test = new NewPasswordActivity();
        TextInputEditText oldPassword = findViewById(R.id.old_password);
        oldPassword.setText("hunter2");
        TextInputEditText newPassword = findViewById(R.id.new_password);
        newPassword.setText("hunter");
        TextInputEditText newPassword2 = findViewById(R.id.new_password_again);
        newPassword2.setText("hunter");
        assertEquals(1,test.changePassword());
    }

    public static void main(String[] args){

        NewPasswordActivity test = new NewPasswordActivity();





    }






}
