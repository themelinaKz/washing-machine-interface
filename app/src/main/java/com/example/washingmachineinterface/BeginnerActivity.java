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

import com.example.washingmachineinterface.favorites.BeginnerWash;
import com.example.washingmachineinterface.favorites.FavoriteItem;

public class BeginnerActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    RadioGroup fabric, color, dirt, allergy;
    RadioButton r_fabric, r_color, r_dirt, r_allergy;
    String s_fabric, s_color, s_dirt, s_allergy;
    boolean is_dirt, is_allergy;
    String dry, temp;

    Dialog dialog; //popup
    TextView t_program, t_colors, t_dirt, t_allergy; //dialog settings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        dialog = new Dialog(this);

        fabric = findViewById(R.id.group_fabric);
        color = findViewById(R.id.group_color);
        dirt = findViewById(R.id.group_dirt);
        allergy = findViewById(R.id.group_allergy);

        ToggleButton favorite = findViewById(R.id.favorite_beginner);
        favorite.setOnCheckedChangeListener(this);
    }

    public void showPopup(View v){
        chooseProgram();

        Button yes;
        Button no;
        dialog.setContentView(R.layout.popup_begin_wash_beginner);

        yes = dialog.findViewById(R.id.b_yes_stop);
        no = dialog.findViewById(R.id.b_no_continue);

        t_program = dialog.findViewById(R.id.program_choice);
        t_colors = dialog.findViewById(R.id.color_choice);
        t_dirt = dialog.findViewById(R.id.dirt_choice);
        t_allergy = dialog.findViewById(R.id.allergy_choice);

        t_program.setText(s_fabric);
        t_colors.setText(s_color);
        t_dirt.setText(s_dirt);
        t_allergy.setText(s_allergy);

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
        Intent main = new Intent(BeginnerActivity.this, MainActivity.class);
        startActivity(main);
    }

    public void toLastScreen(View view){
        Intent last = new Intent(BeginnerActivity.this, LastScreen.class);
        last.putExtra("program",s_fabric);
        last.putExtra("dry",dry);
        last.putExtra("temp", temp);
        last.putExtra("prewash", is_dirt);
        last.putExtra("rinse", is_dirt);
        startActivity(last);
    }

    public void toBeginnerHelp(View view){
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

    private void chooseProgram(){
        r_fabric = findViewById(fabric.getCheckedRadioButtonId());
        s_fabric = r_fabric.getText().toString();
        r_color = findViewById(color.getCheckedRadioButtonId());
        s_color = r_color.getText().toString();
        r_dirt = findViewById(dirt.getCheckedRadioButtonId());
        s_dirt = r_dirt.getText().toString();
        is_dirt = s_dirt.equals(getResources().getString(R.string.s_alot));
        r_allergy = findViewById(allergy.getCheckedRadioButtonId());
        s_allergy = r_allergy.getText().toString();
        is_allergy = s_allergy.equals(getResources().getString(R.string.s_yes));


        if(s_fabric.equals(getResources().getString(R.string.s_cotton_mix))){
            //Cotton mix
            dry = getResources().getString(R.string.s_800rpm);
            if(is_allergy){
                if(is_dirt){
                    temp = getResources().getString(R.string.s_95_celc);
                }else{
                    temp = getResources().getString(R.string.s_70_celc);
                }
            }else{
                if(s_color.equals(getResources().getString(R.string.s_dark))){
                    if(is_dirt){
                        temp = getResources().getString(R.string.s_60_celc);
                    }else{
                        temp = getResources().getString(R.string.s_40_celc);
                    }
                }else if(s_color.equals(getResources().getString(R.string.s_white))){
                    temp = getResources().getString(R.string.s_95_celc);
                }else if(s_color.equals(getResources().getString(R.string.s_colorful))){
                    temp = getResources().getString(R.string.s_60_celc);
                }
            }
        }else if(s_fabric.equals(getResources().getString(R.string.s_synthetic))){
            //Synthetic
            dry = getResources().getString(R.string.s_1000rpm);
            if(s_color.equals(getResources().getString(R.string.s_dark)) || s_color.equals(getResources().getString(R.string.s_colorful))){
                if(!is_dirt && !is_allergy){
                    temp = getResources().getString(R.string.s_40_celc);
                }else {
                    temp = getResources().getString(R.string.s_60_celc);
                }
            }else{
                temp = getResources().getString(R.string.s_60_celc);
            }
        }else if(s_fabric.equals(getResources().getString(R.string.s_delicate))){
            //Delicate
            dry = getResources().getString(R.string.s_0rpm);
            if(s_color.equals(getResources().getString(R.string.s_dark)) || s_color.equals(getResources().getString(R.string.s_colorful))){
                if(!is_dirt && !is_allergy){
                    temp = getResources().getString(R.string.s_30_celc);
                }else {
                    temp = getResources().getString(R.string.s_40_celc);
                }
            }else{
                temp = getResources().getString(R.string.s_40_celc);
            }
        }else if(s_fabric.equals(getResources().getString(R.string.s_wool))){
            //Wool
            dry = getResources().getString(R.string.s_600rpm);
            temp = getResources().getString(R.string.s_40_celc);
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        r_fabric = findViewById(fabric.getCheckedRadioButtonId());
        s_fabric = r_fabric.getText().toString();
        r_color = findViewById(color.getCheckedRadioButtonId());
        s_color = r_color.getText().toString();
        r_dirt = findViewById(dirt.getCheckedRadioButtonId());
        s_dirt = r_dirt.getText().toString();
        is_dirt = s_dirt.equals(getResources().getString(R.string.s_alot));
        r_allergy = findViewById(allergy.getCheckedRadioButtonId());
        s_allergy = r_allergy.getText().toString();
        is_allergy = s_allergy.equals(getResources().getString(R.string.s_yes));

        FavoriteItem item = new BeginnerWash(s_fabric, s_color, is_dirt, is_allergy);
        if(checked){
            MainActivity.addItem(item);
        }else{
            MainActivity.removeItem(item);
        }
    }
}