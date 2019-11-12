package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.cs309.cycloneinsider.R;
import io.reactivex.disposables.Disposable;

public class FeedbackOptionsActivity extends AppCompatActivity {

    private Disposable subscribe;
    private TextView hidden;

    /**
     * Creates activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_options);
        hidden = findViewById(R.id.hidden_feedback_options);
        hidden.setVisibility(View.INVISIBLE);

    }

 /*   @Override
    protected void onDestroy() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }*/

    /**
     * Method sends the checkbox values to the FeedbackActivity when the button Proceed is clicked
     *
     * @param view
     */
    public void Proceed(View view) {

        CheckBox tech = findViewById(R.id.technical_problem);
        CheckBox improve = findViewById(R.id.improvement);
        CheckBox feature = findViewById(R.id.feature);
        CheckBox other = findViewById(R.id.other);

        boolean isCheckedTech = tech.isChecked();
        boolean isCheckedImprove = improve.isChecked();
        boolean isCheckedFeature = feature.isChecked();
        boolean isCheckedOther = other.isChecked();

        hidden.setVisibility(View.INVISIBLE);

        if (Check(isCheckedTech, isCheckedImprove, isCheckedFeature, isCheckedOther)) {

            Intent emailIntent = new Intent(FeedbackOptionsActivity.this, FeedbackActivity.class);
            Bundle extras = new Bundle();
            extras.putBoolean("CHECK_TECH", isCheckedTech);
            extras.putBoolean("CHECK_IMPROVE", isCheckedImprove);
            extras.putBoolean("CHECK_FEATURE", isCheckedFeature);
            extras.putBoolean("CHECK_OTHER", isCheckedOther);
            emailIntent.putExtras(extras);
            startActivity(emailIntent);
            //      return;

        } else {

            hidden.setText("Must check at least one box!");
            hidden.setTextColor(Color.parseColor("#FF0000"));
            hidden.setVisibility(View.VISIBLE);
            return;

        }


    }

    /**
     *Method checks if any of the values are true
     * @param value1 boolean value to check
     * @param value2 2nd boolean value to check
     * @param value3 3rd boolean value to check
     * @param value4 4th boolean value to check
     * @return if either of the values are true
     */
    public boolean Check(boolean value1, boolean value2, boolean value3, boolean value4) {

        if(value1 || value2 || value3 || value4) {

            return true;
        }

        return false;

    }



}
