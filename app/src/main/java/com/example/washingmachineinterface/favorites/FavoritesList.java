package com.example.washingmachineinterface.favorites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.washingmachineinterface.R;

import java.util.ArrayList;

public class FavoritesList extends AppCompatActivity {
    private ArrayList<FavoriteItem> items;
    private RecyclerView favoritesView;
    private FavoritesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_favorites);

        setUpSuggestions();
        setUpRecyclerView();
    }

    public void setUpSuggestions(){
        items = new ArrayList<>();

    }

    private void setUpRecyclerView() {
        favoritesView = findViewById(R.id.favorites_view);
        favoritesView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new FavoritesListAdapter(this, items);

        favoritesView.setLayoutManager(layoutManager);
        favoritesView.setAdapter(adapter);
    }

}