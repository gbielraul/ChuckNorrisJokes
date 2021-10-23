package com.gbielraul.chucknorrisjokes.jokes.presenter;

import android.app.Activity;

import com.gbielraul.chucknorrisjokes.common.presenter.BasePresenter;
import com.gbielraul.chucknorrisjokes.common.presenter.BaseView;
import com.gbielraul.chucknorrisjokes.model.Joke;

public interface JokesContract {

    interface View extends BaseView {
        void setJoke(Joke joke);

        void setCategory(String category);
    }

    interface Presenter extends BasePresenter {
        String getCategoryName(Activity activity);

        void changeJoke(boolean random);
    }
}
