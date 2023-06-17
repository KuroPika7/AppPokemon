package com.si61.apppokemon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.si61.apppokemon.API.APIRequestData;
import com.si61.apppokemon.API.RetroServer;
import com.si61.apppokemon.Adapter.AdapterPokemon;
import com.si61.apppokemon.Model.ModelPokemon;
import com.si61.apppokemon.Model.ModelRespons;
import com.si61.apppokemon.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvPokemon;
    private ProgressBar pbLoading;
    private FloatingActionButton fabTambah;
    private RecyclerView.Adapter adPokemon;
    private RecyclerView.LayoutManager lmPokemon;
    private List<ModelPokemon> listPokemon = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPokemon = findViewById(R.id.rv_pokemon);
        pbLoading = findViewById(R.id.pb_loading);
        fabTambah = findViewById(R.id.fab_tambah);

        lmPokemon = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPokemon.setLayoutManager(lmPokemon);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivities(new Intent[]{new Intent(MainActivity.this, TambahActivity.class)});
            }
        });
    }

    public void retrievePokemon(){
        pbLoading.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelRespons> proses = API.ardRetrieve();

        proses.enqueue(new Callback<ModelRespons>() {
            @Override
            public void onResponse(Call<ModelRespons> call, Response<ModelRespons> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listPokemon = response.body().getData();

                adPokemon = new AdapterPokemon(MainActivity.this, listPokemon);
                rvPokemon.setAdapter(adPokemon);
                adPokemon.notifyDataSetChanged();
                pbLoading.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ModelRespons> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
                pbLoading.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrievePokemon();
    }
}