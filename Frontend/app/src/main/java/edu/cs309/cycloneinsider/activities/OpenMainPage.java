package edu.cs309.cycloneinsider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OpenMainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_main_page);


    }

    public void openDefaultThread(View view) {
        Intent intent = new Intent(this, DefaultForum.class);
        startActivity(intent);
        return;
    }


}