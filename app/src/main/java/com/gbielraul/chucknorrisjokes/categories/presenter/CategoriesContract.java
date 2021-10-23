package com.gbielraul.chucknorrisjokes.categories.presenter;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.gbielraul.chucknorrisjokes.categories.CategoriesActivity;
import com.gbielraul.chucknorrisjokes.common.presenter.BasePresenter;
import com.gbielraul.chucknorrisjokes.common.presenter.BaseView;
import com.gbielraul.chucknorrisjokes.model.Category;

import java.util.List;

public interface CategoriesContract {

    interface View extends BaseView {
        void configureRecyclerView(List<Category> categories);

        void startJokesActivity();

        boolean startJokesActivityWithCategory(int position);

        void showError(@StringRes int msg);
    }

    interface Presenter extends BasePresenter {
        void startJokesScreen();

        void getCategories();

        void onRecyclerItemClickListener(RecyclerView recyclerView, android.view.View view);

        CategoriesActivity.CategoriesViewHolder onCreateViewHolder(Activity activity,
                                                                   @LayoutRes int layout,
                                                                   ViewGroup viewGroup,
                                                                   android.view.View.OnClickListener clickListener);

        void onBindViewHolder(List<Category> categories, CategoriesActivity.CategoriesViewHolder holder, int position);
    }
}
