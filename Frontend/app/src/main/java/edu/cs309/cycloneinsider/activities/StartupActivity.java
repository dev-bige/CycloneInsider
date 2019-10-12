package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import edu.cs309.cycloneinsider.R;
import io.reactivex.disposables.Disposable;

public class StartupActivity extends InsiderActivity {
    private Disposable subscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_startup);
        subscribe = getInsiderApplication()
                .getApiService()
                .currentUser()
                .delay(1, TimeUnit.SECONDS)
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    }
                }, error -> {

                }, () -> {

                });
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }
}
