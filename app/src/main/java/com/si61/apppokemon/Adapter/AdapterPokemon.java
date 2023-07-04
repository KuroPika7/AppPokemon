package com.si61.apppokemon.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.si61.apppokemon.API.APIRequestData;
import com.si61.apppokemon.API.RetroServer;
import com.si61.apppokemon.Activity.MainActivity;
import com.si61.apppokemon.Activity.TambahActivity;
import com.si61.apppokemon.Activity.UbahActivity;
import com.si61.apppokemon.Model.ModelPokemon;
import com.si61.apppokemon.Model.ModelRespons;
import com.si61.apppokemon.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pokemon, parent, false);
        VHPokemon holder = new VHPokemon(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VHPokemon holder, int position) {
        ModelPokemon MP = listpokemon.get(position);

        holder.tvId.setText(MP.getId());
        holder.tvName.setText(MP.getName());
        holder.tvType.setText(MP.getType());
        holder.tvAbility.setText(MP.getAbility());
        holder.tvHeight.setText(MP.getHeight());
        holder.tvWeight.setText(MP.getWeight());
    }

    @Override
    public int getItemCount() {
        return listpokemon.size();
    }

    public class VHPokemon extends RecyclerView.ViewHolder{
        TextView tvId, tvName, tvType, tvAbility, tvHeight, tvWeight;

        public VHPokemon(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvType = itemView.findViewById(R.id.tv_type);
            tvAbility = itemView.findViewById(R.id.tv_ability);
            tvHeight = itemView.findViewById(R.id.tv_height);
            tvWeight = itemView.findViewById(R.id.tv_weight);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda memilih" + tvName. getText() +"Operasi apa yang akan dilakukan?");

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId", tvId.getText().toString());
                            kirim.putExtra("xName", tvName.getText().toString());
                            kirim.putExtra("xType", tvType.getText().toString());
                            kirim.putExtra("xAbility", tvAbility.getText().toString());
                            kirim.putExtra("xHeight", tvHeight.getText().toString());
                            kirim.putExtra("xWeight", tvWeight.getText().toString());
                            ctx.startActivity(kirim);
                        }
                    });

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            prosesHapus(tvId.getText().toString());

                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }
        void prosesHapus(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelRespons> proses = API.ardDelete(id);

            proses.enqueue(new Callback<ModelRespons>() {
                @Override
                public void onResponse(Call<ModelRespons> call, Response<ModelRespons> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : " + kode + " Pesan: " +pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrievePokemon();

                }

                @Override
                public void onFailure(Call<ModelRespons> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server ", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
