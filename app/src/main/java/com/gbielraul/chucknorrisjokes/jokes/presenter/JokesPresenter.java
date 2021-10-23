package com.gbielraul.chucknorrisjokes.jokes.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.gbielraul.chucknorrisjokes.common.Key;
import com.gbielraul.chucknorrisjokes.model.Joke;
import com.gbielraul.chucknorrisjokes.network.ApiCall;
import com.gbielraul.chucknorrisjokes.network.RetrofitSingleton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JokesPresenter implements JokesContract.Presenter{

    private final JokesContract.View view;
    private String category;

    // Presenter constructor
    public JokesPresenter(JokesContract.View view) {
        this.view = view;
    }

    // The first method called from activity
    @Override
    public void start() {
        view.bindViews();
    }

    // Get the name of the category
    @Override
    public String getCategoryName(Activity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        category = "Random";
        if (bundle != null)
            category = bundle.getString(String.valueOf(Key.CATEGORIES_RECYCLER_KEY), "Random");

        view.setCategory(category);

        return category;
    }

    // Get the joke from API and set the text of the joke
    @Override
    public void changeJoke(boolean random) {
        // Get the joke
        Retrofit retrofit = RetrofitSingleton.getInstance();
        ApiCall apiCall = retrofit.create(ApiCall.class);

        // Call
        Call<JsonObject> call;
        if (random) call = apiCall.getJoke("https://api.chucknorris.io/jokes/random");
        else call = apiCall.getJoke("https://api.chucknorris.io/jokes/random?=" + category);

        // The API request
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                // ** LOG **
                Log.i("RESPONSE", "**     " + response.code() + "     " + response.body());

                // Convert from json to Joke Object
                Gson gson = new Gson();
                Joke joke = gson.fromJson(response.body(), Joke.class);

                // Set the text
                view.setJoke(joke);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                // ** LOG **
                Log.e("RESPONSE", t.getMessage());
            }
        });
    }
}
