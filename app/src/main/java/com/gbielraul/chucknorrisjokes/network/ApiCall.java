package com.gbielraul.chucknorrisjokes.network;

import com.gbielraul.chucknorrisjokes.model.Joke;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiCall {

    @GET("categories")
    Call<ArrayList<String>> getCategories();

    @GET
    Call<JsonObject> getJoke(@Url String url);

}
