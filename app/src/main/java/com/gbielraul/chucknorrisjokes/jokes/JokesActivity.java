package com.gbielraul.chucknorrisjokes.jokes;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gbielraul.chucknorrisjokes.R;
import com.gbielraul.chucknorrisjokes.jokes.presenter.JokesContract;
import com.gbielraul.chucknorrisjokes.jokes.presenter.JokesPresenter;
import com.gbielraul.chucknorrisjokes.model.Joke;

public class JokesActivity extends AppCompatActivity implements JokesContract.View {

    private JokesContract.Presenter presenter;

    private ImageView btnBack;
    private TextView txtToolbar;
    private LinearLayout btnReload;

    private String category;
    private TextView txtJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        // Presenter instance
        presenter = new JokesPresenter(this);
        presenter.start();

        // Get the category
        category = presenter.getCategoryName(JokesActivity.this);

        // On back button pressed
        if (btnBack != null)
            btnBack.setOnClickListener(v -> finish());

        // Change the joke
        presenter.changeJoke(category.equals("Random"));

        // When click on reload button, change the joke
        if (btnReload != null)
            btnReload.setOnClickListener(v -> presenter.changeJoke(category.equals("Random")));
    }

    // FVBIng
    @Override
    public void bindViews() {
        btnBack = findViewById(R.id.jokes_button_back);
        txtToolbar = findViewById(R.id.jokes_toolbar_text_view);
        txtJoke = findViewById(R.id.jokes_text_view_joke);
        btnReload = findViewById(R.id.jokes_linear_layout_reload);
    }

    // Set the category text
    @Override
    public void setCategory(String category) {
        if (txtToolbar != null)
            txtToolbar.setText(category);
    }

    // Set the joke text
    @Override
    public void setJoke(Joke joke) {
        if (txtJoke != null)
            txtJoke.setText(joke.getValue());
    }
}