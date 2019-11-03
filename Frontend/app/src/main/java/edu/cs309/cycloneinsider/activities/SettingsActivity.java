package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.cs309.cycloneinsider.R;
import io.reactivex.disposables.Disposable;

public class SettingsActivity extends InsiderActivity {
    private Disposable subscribe;

    /**
     * Creates activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    /**
     * Destroys activity
     */
    @Override
    protected void onDestroy() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }

    /**
     * Method opens an intent for FeedbackOptionsActivity class
     * @param view
     */
    public void openFeedBack(View view) {

        startActivity(new Intent(this, FeedbackOptionsActivity.class));

    }

    /**
     * Method opens an intent for NewPasswordActivity class
     * @param view
     */
    public void openNewPassword(View view) {

        startActivity(new Intent(this, NewPasswordActivity.class));

    }
}
