package com.example.washingmachineinterface.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.washingmachineinterface.R;

import java.util.List;

class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.FavoritesViewHolder> {
    Context context;
    List<FavoriteItem> items;

    public FavoritesListAdapter(@NonNull Context context, @NonNull List<FavoriteItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        final FavoriteItem item = items.get(position);
        holder.program.setText(item.getProgram().toString());
        if(item instanceof BeginnerWash){
            holder.colors.setText(((BeginnerWash) item).getColor().toString());
            holder.dirt.setText(((BeginnerWash) item).getDirt());
            holder.allergy.setText(((BeginnerWash) item).getAllergy());
        }else if(item instanceof AdvancedWash){
            holder.prewash.setText(((AdvancedWash) item).getPrewash());
            holder.temperature.setText(((AdvancedWash) item).getTemperature());
            holder.dry.setText(((AdvancedWash) item).getRpm());
            holder.rinse.setText(((AdvancedWash) item).getRinse());
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO delete favorite
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO begin wash
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class FavoritesViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout item;
        public TextView program;
        public TextView prewash, temperature, dry, rinse; //advanced settings
        public TextView colors, dirt, allergy; //beginner settings
        public ImageButton delete; //delete button

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_layout);
            program = itemView.findViewById(R.id.program_choice);
            delete = itemView.findViewById(R.id.delete_btn);

            prewash = itemView.findViewById(R.id.prewash_choice);
            temperature = itemView.findViewById(R.id.temp_choice);
            dry = itemView.findViewById(R.id.dry_choice);
            rinse = itemView.findViewById(R.id.rinse_choice);

            colors = itemView.findViewById(R.id.color_choice);
            dirt = itemView.findViewById(R.id.dirt_choice);
            allergy = itemView.findViewById(R.id.allergy_choice);

        }
    }
}
