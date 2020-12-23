package com.example.washingmachineinterface;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Dialog dialog; //for popup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        dialog = new Dialog(this);
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

    public void toMainHelp(View view) {
        dialog.setContentView(R.layout.popup_help);
        TextView text = dialog.findViewById(R.id.help);
        text.setText(R.string.s_main_help);
        Button close = dialog.findViewById(R.id.b_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}