package com.tonilopezmr.androidtesting.got.presenter;

import android.os.Handler;
import com.tonilopezmr.androidtesting.got.MVP;
import com.tonilopezmr.androidtesting.got.model.CharacterCollection;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import com.tonilopezmr.androidtesting.got.view.CharacterListView;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CharacterListPresenter implements MVP.Presenter<CharacterListView> {

    private enum ListMode {
        SORTED,
        NOT_SORTED
    }

    private ListMode listMode;

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

    @Override
    public void setView(CharacterListView view) {
        checkNotNull(view, "view must be not null");
        this.view = view;
    }

    public void onSortClick() {
        sortByName();
    }

    public void sortByName() {
        view.showProgressBar();
        listMode = ListMode.SORTED;

        final Handler handler = new Handler();
        new Thread(new Runnable() { //Background
            @Override
            public void run() {
                try {
                    final List<GoTCharacter> characters = domain.sortByName();
                    showCharacters(characters, handler); //Come back to UI thread
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(handler); //Come back to UI thread
                }

            }
        }).start();
    }

    public void loadCharacters() {
        view.showProgressBar();
        listMode = ListMode.NOT_SORTED;

        final Handler handler = new Handler();
        new Thread(new Runnable() { //Background
            @Override
            public void run() {
                try {
                    final List<GoTCharacter> characters = domain.getAll();
                    showCharacters(characters, handler); //Come back to UI thread
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(handler); //Come back to UI thread
                }

            }
        }).start();

    }

    private void showCharacters(final List<GoTCharacter> characters, Handler handler) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (characters.isEmpty()) {
                    view.showEmptyCase();
                    return;
                }
                view.hideProgressBar();
                view.hideEmptyCase();
                view.show(characters);
            }
        });
    }

    private void onError(Handler handler) {
        handler.post(new Runnable() { //UI Thread
            @Override
            public void run() {
                view.hideProgressBar();
                view.error();
            }
        });
    }

}
