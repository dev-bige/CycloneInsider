package edu.cs309.cycloneinsider.activities;

import androidx.appcompat.app.AppCompatActivity;

import edu.cs309.cycloneinsider.CycloneInsiderApp;

public class InsiderActivity extends AppCompatActivity {
    public CycloneInsiderApp getInsiderApplication() {
        return (CycloneInsiderApp) getApplication();
    }
}
