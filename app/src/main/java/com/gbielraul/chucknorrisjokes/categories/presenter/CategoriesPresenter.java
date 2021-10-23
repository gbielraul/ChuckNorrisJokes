package com.gbielraul.chucknorrisjokes.categories.presenter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gbielraul.chucknorrisjokes.R;
import com.gbielraul.chucknorrisjokes.categories.CategoriesActivity;
import com.gbielraul.chucknorrisjokes.model.Category;
import com.gbielraul.chucknorrisjokes.network.ApiCall;
import com.gbielraul.chucknorrisjokes.network.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoriesPresenter implements CategoriesContract.Presenter {

    private final CategoriesContract.View view;

    // Presenter constructor
    public CategoriesPresenter(CategoriesContract.View view) {
        this.view = view;
    }

    // The first method called from view
    @Override
    public void start() {
        view.bindViews();
    }

    // Start the JokesActivity
    @Override
    public void startJokesScreen() {
        view.startJokesActivity();
    }

    // Get the joke categories
    @Override
    public void getCategories() {
        List<Category> categories = new ArrayList<>();

        Retrofit retrofit = RetrofitSingleton.getInstance();

        ApiCall apiCall = retrofit.create(ApiCall.class);

        Call<ArrayList<String>> call = apiCall.getCategories();
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<String>> call, @NonNull Response<ArrayList<String>> response) {
                // ** LOG **
                Log.i("RESPONSE", "**     " + response.code() + "     " + response.body());
                // Adding categories to the list
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Category category = new Category();
                        String c = response.body().get(i);
                        category.setCategory(c.substring(0, 1).toUpperCase() + c.substring(1));
                        categories.add(category);
                        // ** LOG **
                        Log.i("RESPONSE", category.getCategory());
                    }

                    // RecyclerView configurations
                    view.configureRecyclerView(categories);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<String>> call, @NonNull Throwable t) {
                view.showError(R.string.check_the_connection);
                // ** LOG **
                Log.e("RESPONSE", t.getMessage());
            }
        });
    }

    // RecyclerView click listener
    @Override
    public void onRecyclerItemClickListener(RecyclerView recyclerView, View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        // ** LOG **
        Log.i("POSITION", String.valueOf(position));

        // Start activity and pass the category
        if (!this.view.startJokesActivityWithCategory(position))
            this.view.showError(R.string.check_the_connection);
    }

    // Categories adapter configurations
    @Override
    public CategoriesActivity.CategoriesViewHolder onCreateViewHolder(Activity activity, int layout, ViewGroup viewGroup, View.OnClickListener clickListener) {
        View view = activity.getLayoutInflater().inflate(layout, viewGroup, false);
        view.setOnClickListener(clickListener);
        return new CategoriesActivity.CategoriesViewHolder(view);
    }

    // Bind of RecyclerView adapter
    @Override
    public void onBindViewHolder(List<Category> categories, CategoriesActivity.CategoriesViewHolder holder, int position) {
        Category category = categories.get(position);
        // Call the bind method
        holder.bind(category);
    }
}

