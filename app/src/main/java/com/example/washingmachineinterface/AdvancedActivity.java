package com.example.washingmachineinterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.washingmachineinterface.favorites.AdvancedWash;
import com.example.washingmachineinterface.favorites.FavoriteItem;

public class AdvancedActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    RadioGroup fabric, temperature, prewash, dry, rinse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        fabric = findViewById(R.id.group_fabric);
        temperature = findViewById(R.id.group_temp);
        prewash = findViewById(R.id.group_prewash);
        dry = findViewById(R.id.group_dry);
        rinse = findViewById(R.id.group_rinse);

        ToggleButton favorite = findViewById(R.id.favorite_advanced);
        favorite.setOnCheckedChangeListener(this);
    }

    public void mainScreen(View view){
        Intent main = new Intent(AdvancedActivity.this, MainActivity.class);
        startActivity(main);
    }

    public void toLastScreen(View view){
        Intent last = new Intent(AdvancedActivity.this, LastScreen.class);
        startActivity(last);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        RadioButton r_fabric = findViewById(fabric.getCheckedRadioButtonId());
        String s_fabric = r_fabric.getText().toString();

        RadioButton r_temp = findViewById(temperature.getCheckedRadioButtonId());
        String s_temp = r_temp.getText().toString();

        RadioButton r_prewash = findViewById(prewash.getCheckedRadioButtonId());
        String s_prewash = r_prewash.getText().toString();
        boolean is_prewash = s_prewash.equals(getResources().getString(R.string.s_yes));

        RadioButton r_dry = findViewById(dry.getCheckedRadioButtonId());
        String s_dry = r_dry.getText().toString();

        RadioButton r_rinse = findViewById(rinse.getCheckedRadioButtonId());
        String s_rinse = r_rinse.getText().toString();
        boolean is_rinse = s_rinse.equals(getResources().getString(R.string.s_yes));

        FavoriteItem item = new AdvancedWash(s_fabric, is_prewash, s_temp, s_dry, is_rinse);
        if(checked){
            MainActivity.addItem(item);
        }else{
            MainActivity.removeItem(item);
        }
    }
}