package edu.cs309.cycloneinsider.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.sql.Time;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

import edu.cs309.cycloneinsider.R;

public class DefaultForum extends AppCompatActivity {
    private HashMap<String,Integer> dict = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_forum);
        ExplicitWordMap(); //initializes the explicit words in the Map

    }

    private void ExplicitWordMap()
    {
        //String of all the swear words we are using
      String swearWordList =   "anal\n" +
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
      while(scan.hasNextLine()){

          String word = scan.nextLine();
          dict.put(word,1);

      }

    }

    /**
     *Method is used to bold or un-bold text
     */
    public void Bold(View view){

        Switch mySwitch = (Switch) findViewById(R.id.switchBold);
        EditText commentBox = (EditText) findViewById(R.id.poster_comment);
        EditText title = (EditText) findViewById(R.id.post_title);
        boolean isOn = mySwitch.isChecked();
        EditText myText = (EditText) findViewById(R.id.poster_comment);

        if(isOn){

            if(commentBox.hasFocus()){

                commentBox.setTypeface(null,Typeface.BOLD);
                return;
            }

            if(title.hasFocus()){

                title.setTypeface(null,Typeface.BOLD);
                return;

            }

        }
        else {
            if(commentBox.hasFocus()) {

                myText.setTypeface(null, Typeface.NORMAL);
                return;

            }

            if(title.hasFocus()){

                title.setTypeface(null, Typeface.NORMAL);
                return;

            }
        }

    }

    public void PostThread(View view) {

        EditText postTitle = (EditText) findViewById(R.id.post_title); //finds the post title by ID
        EditText initialText = (EditText) findViewById(R.id.poster_comment); //finds the initial comment from poster by ID
        String title = postTitle.getText().toString(); //title of thread as a string
        TextView hiddenText = (TextView) findViewById(R.id.hidden_thread_box); //gets the hidden text box by ID
        String hidden = hiddenText.getText().toString(); //gets the hidden text box as a string
        hiddenText.setVisibility(View.INVISIBLE); //initially has hidden text box invisible
        String initialComments = initialText.getText().toString(); //posters initial comments as a string
        String[] titleWords = title.split(" ",0); //splits up all of the words in the title by spaces
        String[] initialCommentsWords = initialComments.split(" ",0); //splits up all of the initial comments by spaces

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

        Switch s = (Switch) findViewById(R.id.switch1); //finds the explicit switch by id
        Boolean switchState = s.isChecked(); //checks if switch is on or off

        if(!switchState) { //explicit filter is on if false, scans title and initial comments for explicit words

            for (int i = 0; i < titleWords.length; i++) {

                if (dict.containsKey(titleWords[i].toLowerCase())) {

                    hiddenText.setText("Cannot have explicit word: '" + titleWords[i] + "' in the title");
                    hiddenText.setVisibility(View.VISIBLE);
                    return;

                }

            }

            for (int i = 0; i < initialCommentsWords.length; i++) {

                if (dict.containsKey(initialCommentsWords[i].toLowerCase())) {

                    hiddenText.setText("Cannot have explicit word: '" + initialCommentsWords[i] + "' in the initial comments");
                    hiddenText.setVisibility(View.VISIBLE);
                    return;

                }
            }
        }




        //TODO intent goes here

        return;
    }


}