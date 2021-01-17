package com.example.washingmachineinterface.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.washingmachineinterface.R;

import java.util.List;

class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.FavoritesViewHolder> {
    FavoritesList context;
    List<FavoriteItem> items;

    public FavoritesListAdapter(@NonNull FavoritesList context, @NonNull List<FavoriteItem> items) {
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
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, final int position) {
        final FavoriteItem item = items.get(position);
        holder.program.setText(item.getProgram().toString());
        if(item instanceof BeginnerWash){
            holder.colors.setText(((BeginnerWash) item).getColor().toString());
            holder.dirt.setText(((BeginnerWash) item).getDirt());
            holder.allergy.setText(((BeginnerWash) item).getAllergy());

            holder.prewash_cont.setVisibility(View.GONE);
            holder.temperature_cont.setVisibility(View.GONE);
            holder.dry_cont.setVisibility(View.GONE);
            holder.rinse_cont.setVisibility(View.GONE);
        }else if(item instanceof AdvancedWash){
            holder.prewash.setText(((AdvancedWash) item).getPrewash());
            holder.temperature.setText(((AdvancedWash) item).getTemperature());
            holder.dry.setText(((AdvancedWash) item).getRpm());
            holder.rinse.setText(((AdvancedWash) item).getRinse());

            holder.colors_cont.setVisibility(View.GONE);
            holder.dirt_cont.setVisibility(View.GONE);
            holder.allergy_cont.setVisibility(View.GONE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO delete favorite
                context.showDeletePopup(item, position);
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO begin wash
                context.showBeginPopup(item);
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
        public RelativeLayout program_cont;
        public RelativeLayout prewash_cont, temperature_cont, dry_cont, rinse_cont; //advanced containers
        public RelativeLayout colors_cont, dirt_cont, allergy_cont; //beginner containers
        public ImageButton delete; //delete button

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_layout);
            program = itemView.findViewById(R.id.program_choice);
            program_cont = itemView.findViewById(R.id.program_group);
            delete = itemView.findViewById(R.id.delete_btn);

            prewash = itemView.findViewById(R.id.prewash_choice);
            prewash_cont = itemView.findViewById(R.id.prewash_group);
            temperature = itemView.findViewById(R.id.temp_choice);
            temperature_cont = itemView.findViewById(R.id.temp_group);
            dry = itemView.findViewById(R.id.dry_choice);
            dry_cont = itemView.findViewById(R.id.dry_group);
            rinse = itemView.findViewById(R.id.rinse_choice);
            rinse_cont = itemView.findViewById(R.id.rinse_group);

            colors = itemView.findViewById(R.id.color_choice);
            colors_cont = itemView.findViewById(R.id.color_group);
            dirt = itemView.findViewById(R.id.dirt_choice);
            dirt_cont = itemView.findViewById(R.id.dirt_group);
            allergy = itemView.findViewById(R.id.allergy_choice);
            allergy_cont = itemView.findViewById(R.id.allergy_group);

        }
    }
}
