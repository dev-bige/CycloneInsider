package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.cs309.cycloneinsider.R;

public class DefaultForum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_forum);

    }

    public void PostThread(View view) {
        EditText postTitle = (EditText) findViewById(R.id.post_title); //finds the post title by ID
        EditText initialText = (EditText) findViewById(R.id.poster_comment); //finds the initial comment from poster by ID
        String title = postTitle.getText().toString(); //title of thread as a string
        TextView hiddenText = (TextView) findViewById(R.id.hidden_thread_box); //gets the hidden text box by ID
        String hidden = hiddenText.getText().toString(); //gets the hidden text box as a string
        hiddenText.setVisibility(View.INVISIBLE); //initially has hidden text box invisible
        String initialComments = initialText.getText().toString(); //posters initial comments as a string

        if (title.length() == 0 && initialComments.length() == 0) { //poster must enter a title and have an initial comment

            hiddenText.setText("Need to enter a title and place an initial comment");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }

        if (title.length() == 0) { //poster must enter a title

            hiddenText.setText("Need to enter a title");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }

        if (initialComments.length() == 0) { //poster needs to have an initial comment

            hiddenText.setText("Need to enter an initial comment");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }

        int counterTitle = 0;
        int counterComment = 0;

        for (int i = 0; i < title.length(); i++) { //loop finds out if the title is only made up of spaces (this is not allowed)

            if (title.charAt(i) != ' ') {
                break;
            }

            counterTitle++;

        }

        if (counterTitle == title.length()) { //poster needs to enter at least one character

            hiddenText.setText("Need to enter at least one character for title");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }

        for (int i = 0; i < initialComments.length(); i++) { //loop finds out if the initial comments section is only filled with spaces

            if (initialComments.charAt(i) != ' ') {
                break;
            }

            counterComment++;

        }

        if (counterComment == initialComments.length()) { //poster needs to enter at least

            hiddenText.setText("Need to enter at least one character for the initial comment section");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }

        //TODO intent goes here

        return;
    }


}