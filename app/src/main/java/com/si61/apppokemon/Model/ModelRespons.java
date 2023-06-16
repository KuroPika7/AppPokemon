package com.si61.apppokemon.Model;

import java.util.List;

public class ModelRespons {
    private String kode, pesan;
    private List<ModelPokemon> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelPokemon> getData() {
        return data;
    }
}
