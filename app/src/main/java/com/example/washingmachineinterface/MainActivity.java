package com.example.washingmachineinterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toBeginnerActivity(View view){
        Intent beginner = new Intent(MainActivity.this, BeginnerActivity.class);
        startActivity(beginner);
    }

    public void toAdvancedActivity(View view){
        Intent advanced = new Intent(MainActivity.this, AdvancedActivity.class);
        startActivity(advanced);
    }

    public void toLastScreen(View view){
        Intent last = new Intent(MainActivity.this, LastScreen.class);
        startActivity(last);
    }
}