package com.example.washingmachineinterface.favorites;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.washingmachineinterface.BeginnerActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_favorites);

        getList();
        setUpRecyclerView();

        empty_message = findViewById(R.id.empty_msg);
        toggleEmptyMessage();
    }

    public void getList(){
        items = MainActivity.favorites;
        //temporary initialisation
//        items.add(new AdvancedWash(FavoriteItem.Program.Wool.toString(), true, 30, 0, false));
//        items.add(new AdvancedWash(FavoriteItem.Program.Wool, true, 30, 0, false));
//        items.add(new AdvancedWash(FavoriteItem.Program.Cotton, false, 60, 600, false));
//        items.add(new BeginnerWash(FavoriteItem.Program.Synthetic, FavoriteItem.Color.Dark, false, true));
//        items.add(new BeginnerWash(FavoriteItem.Program.Delicate, FavoriteItem.Color.Light, true, false));
//        items.add(new AdvancedWash(FavoriteItem.Program.Cotton, false, 60, 600, false));
    }

    private void setUpRecyclerView() {
        favoritesView = findViewById(R.id.favorites_view);
        favoritesView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new FavoritesListAdapter(this, items);

        favoritesView.setLayoutManager(layoutManager);
        favoritesView.setAdapter(adapter);
    }

    public void toLastScreen(FavoriteItem item){
        if(item instanceof BeginnerWash){
            chooseProgram((BeginnerWash)item);
        }else{
            chooseProgram((AdvancedWash)item);
        }
        Intent last = new Intent(FavoritesList.this, LastScreen.class);
        last.putExtra("program", program);
        last.putExtra("dry",dry);
        last.putExtra("temp", temp);
        last.putExtra("prewash", prewash);
        last.putExtra("rinse", rinse);
        startActivity(last);
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
        prewash = item.isDirt();
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
}