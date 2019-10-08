package edu.cs309.cycloneinsider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.cs309.cycloneinsider.R;
import io.reactivex.disposables.Disposable;

public class FeedbackActivity extends AppCompatActivity {

    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }


    public void sendEmail(View view){

        

    }

    @Override
    protected void onDestroy() {
        if(subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }
}
