package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import io.reactivex.disposables.Disposable;

public class StartupActivity extends InsiderActivity {
    @Inject
    public CycloneInsiderService cycloneInsiderService;
    private Disposable subscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        subscribe = cycloneInsiderService
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
    }

    @Override
    protected void onDestroy() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }
}
