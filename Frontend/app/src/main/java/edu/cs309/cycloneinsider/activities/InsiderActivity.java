package edu.cs309.cycloneinsider.activities;

import androidx.appcompat.app.AppCompatActivity;

import dagger.android.support.DaggerAppCompatActivity;
import edu.cs309.cycloneinsider.CycloneInsiderApp;

public class InsiderActivity extends DaggerAppCompatActivity {
    public CycloneInsiderApp getInsiderApplication() {
        return (CycloneInsiderApp) getApplication();
    }
}
