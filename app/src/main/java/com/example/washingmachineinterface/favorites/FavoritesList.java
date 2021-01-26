package com.example.washingmachineinterface.favorites;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.washingmachineinterface.LastScreen;
import com.example.washingmachineinterface.MainActivity;
import com.example.washingmachineinterface.R;

import java.util.ArrayList;

public class FavoritesList extends AppCompatActivity {
    private ArrayList<FavoriteItem> items;
    private RecyclerView favoritesView;
    private FavoritesListAdapter adapter;

    private TextView empty_message;

    private String program, dry, temp;
    boolean prewash, rinse;

    Switch themeSwitch;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(MainActivity.isSetToNightMode()){
            setTheme(R.style.DarkScreen);
        }else{
            setTheme(R.style.LightScreen);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_favorites);

        themeSwitch = findViewById(R.id.theme_switch);
        themeSwitch.setChecked(MainActivity.isSetToNightMode());
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                MainActivity.setNightMode(checked);
                restartActivity();
            }
        });

        getList();
        setUpRecyclerView();

        empty_message = findViewById(R.id.empty_msg);
        toggleEmptyMessage();
        dialog = new Dialog(this);
    }

    public void getList(){
        items = MainActivity.favorites;
    }

    private void setUpRecyclerView() {
        favoritesView = findViewById(R.id.favorites_view);
        favoritesView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new FavoritesListAdapter(this, items);

        favoritesView.setLayoutManager(layoutManager);
        favoritesView.setAdapter(adapter);
    }

    public void toLastScreen(){
//        if(item instanceof BeginnerWash){
//            chooseProgram((BeginnerWash)item);
//        }else{
//            chooseProgram((AdvancedWash)item);
//        }
        Intent last = new Intent(FavoritesList.this, LastScreen.class);
        last.putExtra("program", program);
        last.putExtra("dry",dry);
        last.putExtra("temp", temp);
        last.putExtra("prewash", prewash);
        last.putExtra("rinse", rinse);
        startActivity(last);
    }

    public void showBeginPopup(final FavoriteItem item){
        if(item instanceof BeginnerWash){
            chooseProgram((BeginnerWash)item);
            dialog.setContentView(R.layout.popup_begin_wash_beginner);
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            TextView t_program = dialog.findViewById(R.id.program_choice);
            TextView t_colors = dialog.findViewById(R.id.color_choice);
            TextView t_dirt = dialog.findViewById(R.id.dirt_choice);
            TextView t_allergy = dialog.findViewById(R.id.allergy_choice);

            t_program.setText(program);
            t_colors.setText(((BeginnerWash) item).getColor());
            t_dirt.setText(((BeginnerWash) item).getDirt());
            t_allergy.setText(((BeginnerWash) item).getAllergy());
        }else{
            chooseProgram((AdvancedWash)item);
            dialog.setContentView(R.layout.popup_begin_wash_advanced);
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            TextView t_program = dialog.findViewById(R.id.program_choice);
            TextView t_prewash = dialog.findViewById(R.id.prewash_choice);
            TextView t_temperature = dialog.findViewById(R.id.temp_choice);
            TextView t_dry = dialog.findViewById(R.id.dry_choice);
            TextView t_rinse = dialog.findViewById(R.id.rinse_choice);

            t_program.setText(program);
            t_prewash.setText(prewash?"Ναι":"Όχι");
            t_temperature.setText(temp);
            t_dry.setText(dry);
            t_rinse.setText(rinse?"Ναι":"Όχι");
        }

        Button yes;
        Button no;

        yes = dialog.findViewById(R.id.b_yes_stop);
        no = dialog.findViewById(R.id.b_no_continue);


//        Log.d("AdvancedActivity", "showPopup: fabric "+s_fabric);
//        Log.d("AdvancedActivity", "showPopup: prewash "+s_prewash);
//        Log.d("AdvancedActivity", "showPopup: temp "+s_temp);
//        Log.d("AdvancedActivity", "showPopup: dry "+s_dry);
//        Log.d("AdvancedActivity", "showPopup: rinse "+s_rinse);

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
                toLastScreen();
            }
        });
        dialog.show();
    }

    public void showDeletePopup(FavoriteItem item, final int position){
        if(item instanceof BeginnerWash){
            chooseProgram((BeginnerWash)item);
            dialog.setContentView(R.layout.popup_delete_beginner);
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            TextView t_program = dialog.findViewById(R.id.program_choice);
            TextView t_colors = dialog.findViewById(R.id.color_choice);
            TextView t_dirt = dialog.findViewById(R.id.dirt_choice);
            TextView t_allergy = dialog.findViewById(R.id.allergy_choice);

            t_program.setText(program);
            t_colors.setText(((BeginnerWash) item).getColor());
            t_dirt.setText(((BeginnerWash) item).getDirt());
            t_allergy.setText(((BeginnerWash) item).getAllergy());
        }else{
            chooseProgram((AdvancedWash)item);
            dialog.setContentView(R.layout.popup_delete_advanced);
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            TextView t_program = dialog.findViewById(R.id.program_choice);
            TextView t_prewash = dialog.findViewById(R.id.prewash_choice);
            TextView t_temperature = dialog.findViewById(R.id.temp_choice);
            TextView t_dry = dialog.findViewById(R.id.dry_choice);
            TextView t_rinse = dialog.findViewById(R.id.rinse_choice);

            t_program.setText(program);
            t_prewash.setText(prewash?"Ναι":"Όχι");
            t_temperature.setText(temp);
            t_dry.setText(dry);
            t_rinse.setText(rinse?"Ναι":"Όχι");
        }

        Button yes;
        Button no;

        yes = dialog.findViewById(R.id.b_yes_stop);
        no = dialog.findViewById(R.id.b_no_continue);

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
                removeItem(position);
            }
        });
        dialog.show();
    }

    public void toFavoritesHelp(View view){
        dialog.setContentView(R.layout.popup_help);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        TextView text = dialog.findViewById(R.id.help);
        text.setText(R.string.s_favorites_help);
        Button close = dialog.findViewById(R.id.b_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void mainScreen(View view){
        Intent main = new Intent(FavoritesList.this, MainActivity.class);
        startActivity(main);
    }

    public void removeItem(int position) {
        MainActivity.removeItem(position);
        favoritesView.removeViewAt(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, items.size());
        toggleEmptyMessage();
    }

    private void toggleEmptyMessage(){
        if(adapter.getItemCount() == 0){
            empty_message.setVisibility(View.VISIBLE);
        }else{
            empty_message.setVisibility(View.GONE);
        }
    }

    private void chooseProgram(BeginnerWash item){
        program = item.getProgram();
        prewash = item.getColor().equals(getResources().getString(R.string.s_white)) || item.isDirt();
        rinse = item.isDirt();

        if(item.getProgram().equals(getResources().getString(R.string.s_cotton_mix))){
            //Cotton mix
            dry = getResources().getString(R.string.s_800rpm);
            if(item.isAllergy()){
                if(item.isDirt()){
                    temp = getResources().getString(R.string.s_95_celc);
                }else{
                    temp = getResources().getString(R.string.s_70_celc);
                }
            }else{
                if(item.getColor().equals(getResources().getString(R.string.s_dark))){
                    if(item.isDirt()){
                        temp = getResources().getString(R.string.s_60_celc);
                    }else{
                        temp = getResources().getString(R.string.s_40_celc);
                    }
                }else if(item.getColor().equals(getResources().getString(R.string.s_white))){
                    temp = getResources().getString(R.string.s_95_celc);
                }else if(item.getColor().equals(getResources().getString(R.string.s_colorful))){
                    temp = getResources().getString(R.string.s_60_celc);
                }
            }
        }else if(item.getProgram().equals(getResources().getString(R.string.s_synthetic))){
            //Synthetic
            dry = getResources().getString(R.string.s_1000rpm);
            if(item.getColor().equals(getResources().getString(R.string.s_dark)) || item.getColor().equals(getResources().getString(R.string.s_colorful))){
                if(!item.isDirt() && !item.isAllergy()){
                    temp = getResources().getString(R.string.s_40_celc);
                }else {
                    temp = getResources().getString(R.string.s_60_celc);
                }
            }else{
                temp = getResources().getString(R.string.s_60_celc);
            }
        }else if(item.getProgram().equals(getResources().getString(R.string.s_delicate))){
            //Delicate
            dry = getResources().getString(R.string.s_0rpm);
            if(item.getColor().equals(getResources().getString(R.string.s_dark)) || item.getColor().equals(getResources().getString(R.string.s_colorful))){
                if(!item.isDirt() && !item.isAllergy()){
                    temp = getResources().getString(R.string.s_30_celc);
                }else {
                    temp = getResources().getString(R.string.s_40_celc);
                }
            }else{
                temp = getResources().getString(R.string.s_40_celc);
            }
        }else if(item.getProgram().equals(getResources().getString(R.string.s_wool))){
            //Wool
            dry = getResources().getString(R.string.s_600rpm);
            temp = getResources().getString(R.string.s_40_celc);
        }
    }

    private void chooseProgram(AdvancedWash item){
        program = item.getProgram();
        dry = item.getRpm();
        temp = item.getTemperature();
        prewash = item.isPrewash();
        rinse = item.isRinse();
    }

    private void restartActivity(){
        Intent it = new Intent(getApplicationContext(), FavoritesList.class);
        startActivity(it);
        finish();
    }
}