package com.tonilopezmr.androidtesting.got.di;

import com.tonilopezmr.androidtesting.got.model.CharacterRepository;
import com.tonilopezmr.androidtesting.got.presenter.CharacterListPresenter;

public class CharacterInjector {

    private static CharacterInjector injector;

    private CharacterListPresenter listPresenter;
    private CharacterRepository characterCollection;

    private CharacterListPresenter characterListPresenter() {
        if (listPresenter == null) {
            return new CharacterListPresenter(characterCollection());
        }

        return listPresenter;
    }

    private CharacterRepository characterCollection() {
        if (characterCollection == null) {
            return new CharacterRepository();
        }

        return characterCollection;
    }

    private void configService(CharacterListPresenter listPresenter) {
        this.listPresenter = listPresenter;
    }

    private void configService(CharacterRepository characterRepository) {
        this.characterCollection = characterRepository;
    }

    public static void load(CharacterInjector injectorLocator) {
        injector = injectorLocator;
    }

    public static void config(CharacterListPresenter listPresenter) {
        injector.configService(listPresenter);
    }

    public static void config(CharacterRepository characterRepository) {
        injector.configService(characterRepository);
    }

    public static CharacterListPresenter injectCharacterListPresenter() {
        return injector.characterListPresenter();
    }

    public static CharacterRepository injectCharacterCollection() {
        return injector.characterCollection();
    }
}

