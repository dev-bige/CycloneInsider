package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.cs309.cycloneinsider.R;
import io.reactivex.disposables.Disposable;

public class FeedbackActivity extends AppCompatActivity {

    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    @Override
    protected void onDestroy() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }

    public void sendEmail(View view) {

        EditText email = findViewById(R.id.feedback_message);
        String emailString = email.getText().toString(); //gets the email in String form
        TextView hidden = findViewById(R.id.hidden_feedback_text);
        hidden.setVisibility(View.INVISIBLE);

        if (emailString.length() == 0) {

            hidden.setText("Cannot leave Feedback box empty");
            hidden.setVisibility(View.VISIBLE);
            return;

        }

        int spacesFlag = 0;

        for (int i = 0; i < emailString.length(); i++) {

            if (emailString.charAt(i) == ' ') {

                spacesFlag++;

            }

        }

        if (spacesFlag == emailString.length()) {

            hidden.setText("Cannot just have spaces in Feedback box");
            hidden.setVisibility(View.VISIBLE);
            return;

        }

        //Sends email
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"cycloneinsider@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback: User");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailString);
        emailIntent.setType("message/rfcc822");
        startActivity(emailIntent);


    }
}
