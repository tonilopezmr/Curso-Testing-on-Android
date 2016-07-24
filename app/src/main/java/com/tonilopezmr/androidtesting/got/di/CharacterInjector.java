package com.tonilopezmr.androidtesting.got.di;

import com.tonilopezmr.androidtesting.got.model.CharacterCollection;
import com.tonilopezmr.androidtesting.got.presenter.CharacterListPresenter;

public class CharacterInjector {

    private static CharacterInjector injector;

    private CharacterListPresenter listPresenter;
    private CharacterCollection characterCollection;

    public CharacterInjector() {
        characterCollection = new CharacterCollection();
        listPresenter = new CharacterListPresenter(characterCollection);
    }

    private CharacterListPresenter characterListPresenter() {
        return listPresenter;
    }

    private CharacterCollection characterCollection() {
        return characterCollection;
    }

    private void configService(CharacterListPresenter listPresenter) {
        this.listPresenter = listPresenter;
    }

    private void configService(CharacterCollection characterCollection) {
        this.characterCollection = characterCollection;
    }

    public static void load(CharacterInjector injectorLocator) {
        injector = injectorLocator;
    }

    public static CharacterInjector injector() {
        return injector;
    }

    public static void config(CharacterListPresenter listPresenter) {
        injector.configService(listPresenter);
    }

    public static void config(CharacterCollection characterCollection) {
        injector.configService(characterCollection);
    }

    public static CharacterListPresenter injectCharacterListPresenter() {
        return injector.characterListPresenter();
    }

    public static CharacterCollection injectCharacterCollection() {
        return injector.characterCollection();
    }
}

