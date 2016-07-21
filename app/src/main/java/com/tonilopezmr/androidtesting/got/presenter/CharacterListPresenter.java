package com.tonilopezmr.androidtesting.got.presenter;

import android.os.Handler;
import com.tonilopezmr.androidtesting.got.MVP;
import com.tonilopezmr.androidtesting.got.model.CharacterCollection;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import com.tonilopezmr.androidtesting.got.view.CharacterListView;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CharacterListPresenter implements MVP.Presenter<CharacterListView> {

    private CharacterListView view;
    private CharacterCollection domain;

    public CharacterListPresenter(CharacterCollection domain) {
        this.domain = domain;
    }

    @Override
    public void init() {
        checkNotNull(view, "must set the view");
        view.initUi();
        loadCharacters();
    }

    public void loadCharacters() {
        final Handler handler = new Handler();

        new Thread(new Runnable() { //Background
            @Override
            public void run () {
                try {
                    final List<GoTCharacter> characters = domain.sortByName();
                    showCharacters(characters, handler);
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(handler);
                }

            }
        }).start();

    }

    private void showCharacters(final List<GoTCharacter> characters, Handler handler) {
        handler.post(new Runnable() {
            @Override
            public void run () {
                view.show(characters);
            }
        });
    }

    private void onError(Handler handler) {
        handler.post(new Runnable() { //UI Thread
            @Override
            public void run () {
                view.error();
            }
        });
    }

    @Override
    public void setView(CharacterListView view) {
        checkNotNull(view, "view must be not null");
        this.view = view;
    }

}
