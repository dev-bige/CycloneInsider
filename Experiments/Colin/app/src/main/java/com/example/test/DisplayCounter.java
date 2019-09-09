package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayCounter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        //Gets the indent of the activity that started this method and extract the string
        Intent intent = getIntent();

    }

    public void counter(View view){

        TextView showCounter = (TextView) findViewById(R.id.textView);
        String countString = showCounter.getText().toString();
        Integer count = Integer.parseInt(countString);
        count++;
        showCounter.setText(count.toString());


    }
}
