package com.tonilopezmr.androidtesting.got.presenter;

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
        domain.getCharacters(new CharacterCollection.Callback(){

            @Override
            public void success(List<GoTCharacter> goTCharacterList) {
                view.show(goTCharacterList);
            }

            @Override
            public void error(Exception ex) {
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
