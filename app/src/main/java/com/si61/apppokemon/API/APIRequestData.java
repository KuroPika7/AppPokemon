package com.si61.apppokemon.API;

import com.si61.apppokemon.Model.ModelRespons;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelRespons> ardRetrieve();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelRespons> ardCreate(
            @Field("name") String name,
            @Field("type") String type,
            @Field("ability") String ability,
            @Field("height") String height,
            @Field("weight") String weight
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ModelRespons> ardDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST
    Call<ModelRespons> ardUpdate(
            @Field("id") String id,
            @Field("name") String name,
            @Field("type") String type,
            @Field("ability") String ability,
            @Field("height") String height,
            @Field("weight") String weight
    );
}
