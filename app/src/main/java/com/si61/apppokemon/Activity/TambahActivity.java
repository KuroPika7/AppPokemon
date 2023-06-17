package com.si61.apppokemon.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.si61.apppokemon.API.APIRequestData;
import com.si61.apppokemon.API.RetroServer;
import com.si61.apppokemon.Model.ModelRespons;
import com.si61.apppokemon.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etType, etAbility, etHeight, etWeight;
    private Button btnTambah;
    private String nama, type, ability, height, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etType = findViewById(R.id.et_type);
        etAbility = findViewById(R.id.et_ability);
        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        btnTambah = findViewById(R.id.btn_tambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = etNama.getText().toString();
                type = etType.getText().toString();
                ability = etAbility.getText().toString();
                height = etHeight.getText().toString();
                weight = etWeight.getText().toString();

                if(nama.trim().isEmpty()) {
                    etNama.setError("Nama Pokemon harus di isi");
                }
                else if(type.trim().isEmpty()){
                    etType.setError("Type Pokemon harus di isi");
                }
                else if(ability.trim().isEmpty()){
                    etAbility.setError("Ability Pokemon harus di isi");
                }
                else if(height.trim().isEmpty()){
                    etHeight.setError("Berat Pokemon harus di isi");
                }
                else if(weight.trim().isEmpty()){
                    etWeight.setError("Tinggi Pokemon harus di isi");
                }
                else{
                    prosesSimpan();

                }

            }
        });
    }

    private void prosesSimpan (){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelRespons> proses = API.ardCreate(nama, type, ability, height, weight);

        proses.enqueue(new Callback<ModelRespons>() {
            @Override
            public void onResponse(Call<ModelRespons> call, Response<ModelRespons> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + " Pesan: "+ pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelRespons> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}