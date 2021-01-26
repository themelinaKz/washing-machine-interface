package com.example.washingmachineinterface;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.washingmachineinterface.favorites.AdvancedWash;
import com.example.washingmachineinterface.favorites.BeginnerWash;
import com.example.washingmachineinterface.favorites.FavoriteItem;
import com.example.washingmachineinterface.favorites.FavoritesList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    Switch themeSwitch;
    Dialog dialog; //for popup
    public static ArrayList<FavoriteItem> favorites = new ArrayList<>();
    private static MainActivity instance;
    // true only on startup
    private static boolean onStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(isSetToNightMode()){
            setTheme(R.style.DarkScreen);
        }else{
            setTheme(R.style.LightScreen);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        themeSwitch = findViewById(R.id.theme_switch);
        themeSwitch.setChecked(isSetToNightMode());
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                setNightMode(checked);
                restartActivity();
            }
        });

        dialog = new Dialog(this);
        instance = this;
        if (onStart){
            initializeFavorites();
            onStart = false;
        }
    }

    public void toBeginnerActivity(View view){
        Intent beginner = new Intent(MainActivity.this, BeginnerActivity.class);
        startActivity(beginner);
    }

    public void toAdvancedActivity(View view){
        Intent advanced = new Intent(MainActivity.this, AdvancedActivity.class);
        startActivity(advanced);
    }

    public void toList(View view){
        Intent list = new Intent(MainActivity.this, FavoritesList.class);
        startActivity(list);
    }

    public void toMainHelp(View view) {
        dialog.setContentView(R.layout.popup_help);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
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

    // called when heart toggle is pressed to save item in list and file
    public static void addItem(FavoriteItem item){
        if(!favorites.contains(item)) {
            //add item to favorites list
            favorites.add(item);

            // gather wash details
            String details;
            if (item instanceof BeginnerWash)
                details = ((BeginnerWash) item).getColor() + "," + ((BeginnerWash) item).getDirt() + "," + ((BeginnerWash) item).getAllergy();
            else
                details = ((AdvancedWash) item).getPrewash() + "," + ((AdvancedWash) item).getTemperature() + "," + ((AdvancedWash) item).getRpm() + "," + ((AdvancedWash) item).getRinse();
            String program = item.getProgram();
            if (program.contains("\n")) program = program.replace("\n", " ");
            String wash = program + "," + details + "\n";

            // add item to favorites.txt file
            File file = new File(instance.getFilesDir(), "favorites.txt");
            try {
                FileWriter writer = new FileWriter(file, true);
                writer.append(wash);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                Log.e("addItem", Log.getStackTraceString(e));
            }
        }
    }

    // called when user deletes item in FavoritesList activity
    public static void removeItem(int position){
        deleteFromFile(favorites.get(position));
        favorites.remove(position);
    }

    // called when heart toggle is pressed to undo saving
    public static void removeItem(FavoriteItem item){
        deleteFromFile(item);
        favorites.remove(item);
    }

    // delete item from favorites.txt
    private static void deleteFromFile(FavoriteItem item){
        File file = new File(instance.getFilesDir(),"favorites.txt");
        if (!file.exists()) return;
        try {
            File temp = new File(instance.getFilesDir(),"temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
            String line = reader.readLine();
            FavoriteItem line_item;

            // copy file to temp without item
            while(line!=null){
                // if line is found
                line_item = lineToFavoriteItem(line);
                Log.d("TOKENIZER", String.valueOf(line_item instanceof AdvancedWash));
                if (item instanceof  BeginnerWash && line_item instanceof  BeginnerWash){
                    // if item is found skip it
                    if (item.equals(line_item)){
                        line = reader.readLine();
                        continue;
                    }
                }
                else if (item instanceof  AdvancedWash && line_item instanceof  AdvancedWash){
                    // if item is found skip it
                    if (item.equals(line_item)){
                        line = reader.readLine();
                        continue;
                    }
                }
                writer.append(line + "\n");
                line = reader.readLine();
            }
            reader.close();
            writer.close();
            temp.renameTo(file);
        } catch (IOException e) {
            Log.e("deleteFromFile", Log.getStackTraceString(e));
        }
    }

    // initialize favorites list from file on startup
    private static void initializeFavorites(){
        File file = new File(instance.getFilesDir(), "favorites.txt");
        if (!file.exists())
            return;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            FavoriteItem wash;
            while(line!=null){
                wash = lineToFavoriteItem(line);
                favorites.add(wash);
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e("initializeFavorites",Log.getStackTraceString(e));
        }
    }

    // returns FavoriteItem stored as string in tokenizer
    private static FavoriteItem lineToFavoriteItem(String line){
        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        String program = tokenizer.nextToken();
        if(program.equals(instance.getResources().getString(R.string.s_cotton_mix).replace("\n"," ")))
            program = instance.getResources().getString(R.string.s_cotton_mix);
        FavoriteItem line_item = null;
        
        // if line is BeginnerWash
        if (tokenizer.countTokens()==3){
            String color = tokenizer.nextToken();
            boolean dirt = (tokenizer.nextToken().equals("Πολύ"));
            boolean allergy = (tokenizer.nextToken().equals("Ναι"));
            line_item = new BeginnerWash(program,color,dirt,allergy);
        }
        else if (tokenizer.countTokens()==4){
            boolean prewash = tokenizer.nextToken().equals("Ναι");
            String temperature = tokenizer.nextToken();
            String rpm = tokenizer.nextToken();
            boolean rinse = (tokenizer.nextToken().equals("Ναι"));
            line_item = new AdvancedWash(program,prewash,temperature,rpm,rinse);
        }
        return line_item;
    }

    public static boolean isSetToNightMode(){
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    }

    public static void setNightMode(boolean night){
        if(night){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void restartActivity(){
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
//        finish();
    }
}