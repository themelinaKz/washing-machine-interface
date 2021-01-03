package com.example.washingmachineinterface;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.washingmachineinterface.favorites.FavoriteItem;
import com.example.washingmachineinterface.favorites.FavoritesList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Dialog dialog; //for popup
    public static ArrayList<FavoriteItem> favorites = new ArrayList<>();

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

    public void toList(View view){
        Intent list = new Intent(MainActivity.this, FavoritesList.class);
        startActivity(list);
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

    public static void addItem(FavoriteItem item){
        if(!favorites.contains(item)){
            favorites.add(item);
        }
    }

    public static void removeItem(int position){
        favorites.remove(position);
    }

    public static void removeItem(FavoriteItem item){
        favorites.remove(item);
    }
}