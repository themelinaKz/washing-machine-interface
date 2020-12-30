package com.example.washingmachineinterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdvancedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void mainScreen(View view){
        Intent main = new Intent(AdvancedActivity.this, MainActivity.class);
        startActivity(main);
    }

    public void toLastScreen(View view){
        Intent last = new Intent(AdvancedActivity.this, LastScreen.class);
        startActivity(last);
    }
}