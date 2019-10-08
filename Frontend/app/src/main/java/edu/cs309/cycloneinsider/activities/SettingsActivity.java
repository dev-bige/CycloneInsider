package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.PostCreateRequestModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SettingsActivity extends InsiderActivity {
    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    public void openFeedBack(View view){

        startActivity(new Intent(this,FeedbackActivity.class));

    }



    @Override
    protected void onDestroy() {
        if(subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }
}
