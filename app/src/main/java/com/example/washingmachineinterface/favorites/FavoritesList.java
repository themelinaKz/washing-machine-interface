package com.example.washingmachineinterface.favorites;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_favorites);

        setUpList();
        setUpRecyclerView();

        empty_message = findViewById(R.id.empty_msg);
        toggleEmptyMessage();
    }

    public void setUpList(){
        items = new ArrayList<>();
        //temporary initialisation
        items.add(new AdvancedWash(FavoriteItem.Program.Wool, true, 30, 0, false));
        items.add(new AdvancedWash(FavoriteItem.Program.Cotton, false, 60, 600, false));
        items.add(new BeginnerWash(FavoriteItem.Program.Synthetic, FavoriteItem.Color.Dark, false, true));
        items.add(new BeginnerWash(FavoriteItem.Program.Delicate, FavoriteItem.Color.Light, true, false));
        items.add(new AdvancedWash(FavoriteItem.Program.Cotton, false, 60, 600, false));
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
        Intent last = new Intent(FavoritesList.this, LastScreen.class);
        startActivity(last);
    }

    public void mainScreen(View view){
        Intent main = new Intent(FavoritesList.this, MainActivity.class);
        startActivity(main);
    }

    public void removeItem(int position) {
        items.remove(position);
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
}