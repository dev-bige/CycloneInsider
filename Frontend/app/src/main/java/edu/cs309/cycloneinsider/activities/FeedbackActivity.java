package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Scanner;

import edu.cs309.cycloneinsider.R;
import io.reactivex.disposables.Disposable;

public class FeedbackActivity extends AppCompatActivity {

    private Disposable subscribe;
    private HashMap<String, Integer> dict = new HashMap<>();

    /**
     * Method contains the list of all explicit words to check for
     */
    private void ExplicitWordMap() {
        //String of all the swear words we are using
        String swearWordList = "anal\n" +
                "anus\n" +
                "arse\n" +
                "ass\n" +
                "ballsack\n" +
                "balls\n" +
                "bastard\n" +
                "bitch\n" +
                "biatch\n" +
                "bloody\n" +
                "blowjob\n" +
                "bollock\n" +
                "bollok\n" +
                "boner\n" +
                "boob\n" +
                "bugger\n" +
                "bum\n" +
                "butt\n" +
                "buttplug\n" +
                "clitoris\n" +
                "cock\n" +
                "coon\n" +
                "crap\n" +
                "cunt\n" +
                "damn\n" +
                "dick\n" +
                "dildo\n" +
                "dyke\n" +
                "fag\n" +
                "feck\n" +
                "fellate\n" +
                "fellatio\n" +
                "felching\n" +
                "fuck\n" +
                "fudgepacker\n" +
                "flange\n" +
                "Goddamn\n" +
                "God damn\n" +
                "hell\n" +
                "homo\n" +
                "jerk\n" +
                "jizz\n" +
                "knobend\n" +
                "knob end\n" +
                "labia\n" +
                "lmao\n" +
                "lmfao\n" +
                "muff\n" +
                "nigger\n" +
                "nigga\n" +
                "omg\n" +
                "penis\n" +
                "piss\n" +
                "poop\n" +
                "prick\n" +
                "pube\n" +
                "pussy\n" +
                "queer\n" +
                "scrotum\n" +
                "sex\n" +
                "shit\n" +
                "sh1t\n" +
                "slut\n" +
                "smegma\n" +
                "spunk\n" +
                "tit\n" +
                "tosser\n" +
                "turd\n" +
                "twat\n" +
                "vagina\n" +
                "wank\n" +
                "whore\n" +
                "wtf";

        //puts all of the swear words into a HashMap
        Scanner scan = new Scanner(swearWordList);
        while (scan.hasNextLine()) {

            String word = scan.nextLine();
            dict.put(word, 1);

        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ExplicitWordMap(); //initializes the explicit words in the Map
    }

    @Override
    protected void onDestroy() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }

    /**
     * Method checks the email for explicit words
     * @param text email String
     * @return true if email contains no explicit words otherwise, false
     */
    public boolean checkEmail(String text) {

        String[] words = text.split(" ", 0);
        for (int i = 0; i < words.length; i++) {

            if (dict.containsKey(words[i])) {

                return false;

            }

        }
        return true;


    }

    /**
     * Method concatenates what the subject heading will be for the sendFeedback email
     * @param val1 true if "Technical" should be in the Subject otherwise, null
     * @param val2 true if "Improve" should be in the Subject otherwise, null
     * @param val3 true if "Features" should be in the Subject otherwise, null
     * @param val4 true if "Other" should be in the Subject oherwise, null
     * @return what the Subject heading should be in the email
     */
    public String CheckSubject(boolean val1, boolean val2, boolean val3, boolean val4){

        String subject = "";
        if (val1) {

            subject += "Technical: ";

        }

        if (val2) {

            subject += "Improve: ";

        }

        if (val3) {

            subject += "Feature: ";

        }

        if (val4) {

            subject += "Other: ";

        }

        subject += "(User Feedback)";

        return subject;

    }

    public boolean check(String text){

        if(text.length() == 0){
            return false;
        }

        int spacesFlag = 0;

        for (int i = 0; i < text.length(); i++) {

            if (text.charAt(i) == ' ') {

                spacesFlag++;

            }

        }

        if(spacesFlag == text.length()){
            return false;
        }
        return true;


    }

    /**
     * Method checks if the text of words can be sent and then sends the email to the Google Gmail app
     *
     * @param view
     */
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

        if(!checkEmail(emailString)){
            hidden.setText("Email content cannot contain explicit words");
            hidden.setVisibility(View.VISIBLE);
            return;
        }

        //Sends email

        View inflatedView = getLayoutInflater().inflate(R.layout.activity_feedback_options, null);

        CheckBox tech = inflatedView.findViewById(R.id.technical_problem);
        CheckBox improve = inflatedView.findViewById(R.id.improvement);
        CheckBox feature = inflatedView.findViewById(R.id.feature);
        CheckBox other = inflatedView.findViewById(R.id.other);

       /* boolean isCheckedTech = tech.isChecked();
        boolean isCheckedImprove = improve.isChecked();
        boolean isCheckedFeature = feature.isChecked();
        boolean isCheckedOther = other.isChecked();*/

        Bundle extras = getIntent().getExtras();
        boolean isCheckedTech = extras.getBoolean("CHECK_TECH");
        boolean isCheckedImprove = extras.getBoolean("CHECK_IMPROVE");
        boolean isCheckedFeature = extras.getBoolean("CHECK_FEATURE");
        boolean isCheckedOther = extras.getBoolean("CHECK_OTHER");

        String Subject = "";

        if (isCheckedTech) {

            Subject += "Technical: ";

        }

        if (isCheckedImprove) {

            Subject += "Improve: ";

        }

        if (isCheckedFeature) {

            Subject += "Feature: ";

        }

        if (isCheckedOther) {

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
