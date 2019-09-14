package edu.cs309.cycloneinsider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void openMainPage(View view){

        Intent intent = new Intent(this, OpenMainPage.class); //starts an indent
        EditText netID = (EditText) findViewById(R.id.net_id); //finds the user name in netID box by ID
        EditText password = (EditText) findViewById(R.id.password); //finds the password in password box by ID
        TextView hiddenText = (TextView) findViewById(R.id.hidden_text); //finds the hidden text box that communicates errors to user when entering wrong values
        hiddenText.setVisibility(View.INVISIBLE); //makes sure to have the text box INVISIBLE, unless a conditional statement triggers it to be VISIBLE
        String netIDString = netID.getText().toString(); //extracts the string of the netID
        String passwordString = password.getText().toString(); //extracts the string of the password
        String hiddenTextString = hiddenText.getText().toString(); //extracts the string value of hiddenText in order to set this value when users enter bad username/passwords

        if(netIDString.length() == 0 && passwordString.length() == 0){ //Net ID and Password must be entered

            hiddenText.setText("You must enter your Net ID and Password");
            hiddenText.setVisibility(View.VISIBLE); //sets the error box to VISIBLE
            return;

        }

        else if(netIDString.length() == 0){ //Net ID must be entered

            hiddenText.setText("You must enter your Net ID");
            hiddenText.setVisibility(View.VISIBLE);
            return;

        }

        else if(passwordString.length() == 0){ //Password must be entered

            hiddenText.setText("You must enter your Password");
            hiddenText.setVisibility(View.VISIBLE);
            return;

        }

       startActivity(intent); //opens up the main page

    }
}
