package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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

    public boolean check(String text){

        String[] texts = text.split(" ",0);

        if (texts.length == 0) {

            return false;

        }

        int spacesFlag = 0;

        for (int i = 0; i < texts.length; i++) {

            if (texts[i] == " ") {

                spacesFlag++;

            }

        }

        if (spacesFlag == texts.length) {

            return false;

        }


        return true;

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

        View inflatedView = getLayoutInflater().inflate(R.layout.activity_feedback_options,null);

        CheckBox tech = (CheckBox) inflatedView.findViewById(R.id.technical_problem);
        CheckBox improve = (CheckBox) inflatedView.findViewById(R.id.improvement);
        CheckBox feature = (CheckBox) inflatedView.findViewById(R.id.feature);
        CheckBox other = (CheckBox) inflatedView.findViewById(R.id.other);

        boolean isCheckedTech = tech.isChecked();
        boolean isCheckedImprove = improve.isChecked();
        boolean isCheckedFeature = feature.isChecked();
        boolean isCheckedOther = other.isChecked();

        String Subject = "";

        if(isCheckedTech){

            Subject += "Technical: ";

        }

        if(isCheckedImprove){

            Subject += "Improve: ";

        }

        if(isCheckedFeature){

            Subject += "Feature: ";

        }

        if(isCheckedOther){

            Subject += "Other: ";

        }

        Subject += "(User Feedback)";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"cycloneinsider@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, Subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailString);
        emailIntent.setType("message/rfcc822");
        startActivity(emailIntent);


    }
}
