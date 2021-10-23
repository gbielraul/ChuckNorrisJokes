package com.gbielraul.chucknorrisjokes.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gbielraul.chucknorrisjokes.R;
import com.gbielraul.chucknorrisjokes.categories.presenter.CategoriesContract;
import com.gbielraul.chucknorrisjokes.categories.presenter.CategoriesPresenter;
import com.gbielraul.chucknorrisjokes.common.Key;
import com.gbielraul.chucknorrisjokes.jokes.JokesActivity;
import com.gbielraul.chucknorrisjokes.model.Category;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements CategoriesContract.View {

    private CategoriesContract.Presenter presenter;

    private TextView txtRandom;
    private RecyclerView recyclerView;

    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Start presenter
        presenter = new CategoriesPresenter(this);
        presenter.start();
        presenter.getCategories();

        // Set a click listener on the random button
        if (txtRandom != null)
            txtRandom.setOnClickListener(v -> presenter.startJokesScreen());
    }

    // FVBIng
    @Override
    public void bindViews() {
        recyclerView = findViewById(R.id.categories_recycler_view);
        txtRandom = findViewById(R.id.categories_text_view_random);
    }

    // Start JokesActivity
    @Override
    public void startJokesActivity() {
        Intent intent = new Intent(CategoriesActivity.this, JokesActivity.class);
        startActivity(intent);
    }

    // Start JokesActivity and pass the category
    @Override
    public boolean startJokesActivityWithCategory(int position) {
        if (categories != null) {
            Intent intent = new Intent(CategoriesActivity.this, JokesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(
                    String.valueOf(Key.CATEGORIES_RECYCLER_KEY),
                    categories.get(position).getCategory());
            intent.putExtras(bundle);

            startActivity(intent);
            return true;
        }
        return false;
    }

    // RecyclerView configuration
    @Override
    public void configureRecyclerView(List<Category> categories) {
        this.categories = categories;
        recyclerView.setAdapter(new CategoriesActivity.CategoriesAdapter(categories));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
    }

    // Display an error Toast
    @Override
    public void showError(@StringRes int msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    // Click listener of the RecyclerView
    private final View.OnClickListener onRecyclerClickListener = view -> presenter.onRecyclerItemClickListener(recyclerView, view);

    // RecyclerView adapter
    private class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

        private final List<Category> categories;

        private CategoriesAdapter(List<Category> categories) {
            this.categories = categories;
        }

        @NonNull
        @Override
        public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Layout inflating
            return presenter.onCreateViewHolder(CategoriesActivity.this, R.layout.item_category, parent, onRecyclerClickListener);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
            presenter.onBindViewHolder(categories, holder, position);
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }
    }

    // RecyclerView view holder
    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        // ViewHolder constructor
        public CategoriesViewHolder(View itemView) {
            super(itemView);

            // FVBIng
            textView = itemView.findViewById(R.id.text_view_category);
        }

        // ViewHolder bind
        public void bind(Category category) {
            textView.setText(category.getCategory());
        }
    }
}