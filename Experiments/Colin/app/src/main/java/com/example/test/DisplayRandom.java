package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayRandom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        //Gets the indent of the activity that started this method and extract the string
        Intent intent = getIntent();

    }

    public void random(View view){

        TextView showNum = (TextView) findViewById(R.id.textView);
        String numString = showNum.getText().toString();
        double x = (Math.random()*((1000-0)+1))+0;
        String value = x+"";
        showNum.setText(value.toString());


    }
}
