package com.si61.apppokemon.API;

import com.si61.apppokemon.Model.ModelRespons;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelRespons> ardRetrieve();
}
