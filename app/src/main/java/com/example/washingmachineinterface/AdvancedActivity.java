package com.example.washingmachineinterface;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.washingmachineinterface.favorites.AdvancedWash;
import com.example.washingmachineinterface.favorites.FavoriteItem;

public class AdvancedActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    RadioGroup fabric, temperature, prewash, dry, rinse;
    RadioButton r_fabric, r_temp, r_prewash, r_dry, r_rinse;
    String s_fabric, s_temp, s_prewash, s_dry, s_rinse;
    boolean is_prewash, is_rinse;

    Dialog dialog;
    TextView t_program, t_prewash, t_temperature, t_dry, t_rinse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        dialog = new Dialog(this);

        fabric = findViewById(R.id.group_fabric);
        temperature = findViewById(R.id.group_temp);
        prewash = findViewById(R.id.group_prewash);
        dry = findViewById(R.id.group_dry);
        rinse = findViewById(R.id.group_rinse);

        ToggleButton favorite = findViewById(R.id.favorite_advanced);
        favorite.setOnCheckedChangeListener(this);
    }

    public void showPopup(View v){
        chooseProgram();

        Button yes;
        Button no;
        dialog.setContentView(R.layout.popup_begin_wash_advanced);

        yes = dialog.findViewById(R.id.b_yes_stop);
        no = dialog.findViewById(R.id.b_no_continue);

        t_program = dialog.findViewById(R.id.program_choice);
        t_prewash = dialog.findViewById(R.id.prewash_choice);
        t_temperature = dialog.findViewById(R.id.temp_choice);
        t_dry = dialog.findViewById(R.id.dry_choice);
        t_rinse = dialog.findViewById(R.id.rinse_choice);

        t_program.setText(s_fabric);
        t_prewash.setText(s_prewash);
        t_temperature.setText(s_temp);
        t_dry.setText(s_dry);
        t_rinse.setText(s_rinse);
        Log.d("AdvancedActivity", "showPopup: fabric "+s_fabric);
        Log.d("AdvancedActivity", "showPopup: prewash "+s_prewash);
        Log.d("AdvancedActivity", "showPopup: temp "+s_temp);
        Log.d("AdvancedActivity", "showPopup: dry "+s_dry);
        Log.d("AdvancedActivity", "showPopup: rinse "+s_rinse);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                toLastScreen(v);
            }
        });
        dialog.show();
    }

    public void mainScreen(View view){
        Intent main = new Intent(AdvancedActivity.this, MainActivity.class);
        startActivity(main);
    }

    public void toLastScreen(View view){
        Intent last = new Intent(AdvancedActivity.this, LastScreen.class);
        last.putExtra("program",s_fabric);
        last.putExtra("dry",s_dry);
        last.putExtra("temp", s_temp);
        last.putExtra("prewash", is_prewash);
        last.putExtra("rinse", is_rinse);
        startActivity(last);
    }

    public void toAdvancedHelp(View view){
        dialog.setContentView(R.layout.popup_help);
        TextView text = dialog.findViewById(R.id.help);
        text.setText(R.string.s_choices_help);
        Button close = dialog.findViewById(R.id.b_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        r_fabric = findViewById(fabric.getCheckedRadioButtonId());
        s_fabric = r_fabric.getText().toString();

        r_temp = findViewById(temperature.getCheckedRadioButtonId());
        s_temp = r_temp.getText().toString();

        r_prewash = findViewById(prewash.getCheckedRadioButtonId());
        s_prewash = r_prewash.getText().toString();
        is_prewash = s_prewash.equals(getResources().getString(R.string.s_yes));

        r_dry = findViewById(dry.getCheckedRadioButtonId());
        s_dry = r_dry.getText().toString();

        r_rinse = findViewById(rinse.getCheckedRadioButtonId());
        s_rinse = r_rinse.getText().toString();
        is_rinse = s_rinse.equals(getResources().getString(R.string.s_yes));

        FavoriteItem item = new AdvancedWash(s_fabric, is_prewash, s_temp, s_dry, is_rinse);
        if(checked){
            MainActivity.addItem(item);
        }else{
            MainActivity.removeItem(item);
        }
    }

    private void chooseProgram(){
        r_fabric = findViewById(fabric.getCheckedRadioButtonId());
        s_fabric = r_fabric.getText().toString();

        r_temp = findViewById(temperature.getCheckedRadioButtonId());
        s_temp = r_temp.getText().toString();

        r_prewash = findViewById(prewash.getCheckedRadioButtonId());
        s_prewash = r_prewash.getText().toString();
        is_prewash = s_prewash.equals(getResources().getString(R.string.s_yes));

        r_dry = findViewById(dry.getCheckedRadioButtonId());
        s_dry = r_dry.getText().toString();

        r_rinse = findViewById(rinse.getCheckedRadioButtonId());
        s_rinse = r_rinse.getText().toString();
        is_rinse = s_rinse.equals(getResources().getString(R.string.s_yes));
    }
}