package edu.cs309.cycloneinsider;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

public class OpenMainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_main_page);


    }

    public void openDefaultThread(View view)
    {
        Intent intent = new Intent(this, DefaultForum.class);
        startActivity(intent);
        return;
    }




}