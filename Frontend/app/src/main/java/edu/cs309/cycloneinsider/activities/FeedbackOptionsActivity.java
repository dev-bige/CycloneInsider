package edu.cs309.cycloneinsider.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.w3c.dom.Text;

import edu.cs309.cycloneinsider.R;
import io.reactivex.disposables.Disposable;

public class FeedbackOptionsActivity extends AppCompatActivity {

    private Disposable subscribe;
    private TextView hidden;


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


    public void Proceed(View view){

        CheckBox tech = findViewById(R.id.technical_problem);
        CheckBox improve = findViewById(R.id.improvement);
        CheckBox feature = findViewById(R.id.feature);
        CheckBox other = findViewById(R.id.other);

        boolean isCheckedTech = tech.isChecked();
        boolean isCheckedImprove = improve.isChecked();
        boolean isCheckedFeature = feature.isChecked();
        boolean isCheckedOther = other.isChecked();

        hidden.setVisibility(View.INVISIBLE);

        if(isCheckedTech || isCheckedImprove || isCheckedFeature || isCheckedOther) {

            Intent emailIntent = new Intent(FeedbackOptionsActivity.this, FeedbackActivity.class);
            startActivity(emailIntent);
      //      return;

        }

        else{

            hidden.setText("Must check at least one box!" );
            hidden.setTextColor(Color.parseColor("#FF0000"));
            hidden.setVisibility(View.VISIBLE);
            return;

        }


    }




}
