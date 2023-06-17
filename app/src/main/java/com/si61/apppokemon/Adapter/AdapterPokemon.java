package com.si61.apppokemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.si61.apppokemon.Model.ModelPokemon;
import com.si61.apppokemon.R;

import java.util.List;

public class AdapterPokemon extends RecyclerView.Adapter<AdapterPokemon.VHPokemon>{
    private Context ctx;
    private List<ModelPokemon> listpokemon;

    public AdapterPokemon(Context ctx, List<ModelPokemon> listpokemon) {
        this.ctx = ctx;
        this.listpokemon = listpokemon;
    }

    @NonNull
    @Override
    public VHPokemon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_pokemon, parent, false);
        return new VHPokemon(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHPokemon holder, int position) {
        ModelPokemon MP = listpokemon.get(position);

        holder.tvId.setText(MP.getId());
        holder.tvName.setText(MP.getName());
    }

    @Override
    public int getItemCount() {
        return listpokemon.size();
    }

    public class VHPokemon extends RecyclerView.ViewHolder{
        TextView tvId, tvName;

        public VHPokemon(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
