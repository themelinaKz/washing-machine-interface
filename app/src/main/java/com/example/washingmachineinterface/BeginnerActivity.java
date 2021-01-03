package com.example.washingmachineinterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.washingmachineinterface.favorites.BeginnerWash;
import com.example.washingmachineinterface.favorites.FavoriteItem;

public class BeginnerActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    RadioGroup fabric, color, dirt, allergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        fabric = findViewById(R.id.group_fabric);
        color = findViewById(R.id.group_color);
        dirt = findViewById(R.id.group_dirt);
        allergy = findViewById(R.id.group_allergy);

        ToggleButton favorite = findViewById(R.id.favorite_beginner);
        favorite.setOnCheckedChangeListener(this);
    }

    public void mainScreen(View view){
        Intent main = new Intent(BeginnerActivity.this, MainActivity.class);
        startActivity(main);
    }

    public void toLastScreen(View view){
        Intent last = new Intent(BeginnerActivity.this, LastScreen.class);
        startActivity(last);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        RadioButton r_fabric = findViewById(fabric.getCheckedRadioButtonId());
        String s_fabric = r_fabric.getText().toString();
        RadioButton r_color = findViewById(color.getCheckedRadioButtonId());
        String s_color = r_color.getText().toString();
        RadioButton r_dirt = findViewById(dirt.getCheckedRadioButtonId());
        String s_dirt = r_dirt.getText().toString();
        boolean is_dirt = s_dirt.equals(getResources().getString(R.string.s_alot));
        RadioButton r_allergy = findViewById(allergy.getCheckedRadioButtonId());
        String s_allergy = r_allergy.getText().toString();
        boolean is_allergy = s_allergy.equals(getResources().getString(R.string.s_yes));

        FavoriteItem item = new BeginnerWash(s_fabric, s_color, is_dirt, is_allergy);
        if(checked){
            MainActivity.addItem(item);
        }else{
            MainActivity.removeItem(item);
        }
    }
}