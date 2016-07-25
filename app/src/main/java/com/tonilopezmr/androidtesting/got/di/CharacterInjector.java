package com.tonilopezmr.androidtesting.got.di;

import com.tonilopezmr.androidtesting.got.model.CharacterRepository;
import com.tonilopezmr.androidtesting.got.model.api.CharacterApi;
import com.tonilopezmr.androidtesting.got.model.validator.CharacterValidator;
import com.tonilopezmr.androidtesting.got.presenter.CharacterListPresenter;

public class CharacterInjector {

    private static CharacterInjector injector;

    private CharacterListPresenter listPresenter;
    private CharacterRepository characterRepository;
    private CharacterValidator characterValidator;
    private CharacterApi characterApi;

    private CharacterValidator characterValidator() {
        if (characterValidator == null) {
            return new CharacterValidator();
        }

        return characterValidator;
    }

    private CharacterApi characterApi() {
        if (characterApi == null) {
            return new CharacterApi();
        }

        return characterApi;
    }

    private CharacterRepository characterRepository() {
        if (characterRepository == null) {
            return new CharacterRepository(characterValidator(),  characterApi());
        }

        return characterRepository;
    }

    private CharacterListPresenter characterListPresenter() {
        if (listPresenter == null) {
            return new CharacterListPresenter(characterRepository());
        }

        return listPresenter;
    }

    private void configService(CharacterValidator characterValidator) {
        this.characterValidator = characterValidator;
    }

    private void configService(CharacterApi characterApi) {
        this.characterApi = characterApi;
    }

    private void configService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    private void configService(CharacterListPresenter listPresenter) {
        this.listPresenter = listPresenter;
    }


    /////////////////////////////
    ////      Public API     ////
    /////////////////////////////

    public static void load(CharacterInjector injectorLocator) {
        injector = injectorLocator;
    }

    public static void config(CharacterListPresenter listPresenter) {
        injector.configService(listPresenter);
    }

    public static void config(CharacterRepository characterRepository) {
        injector.configService(characterRepository);
    }

    public static void config(CharacterValidator characterValidator) {
        injector.configService(characterValidator);
    }

    public static void config(CharacterApi characterApi) {
        injector.configService(characterApi);
    }

    public static CharacterListPresenter injectCharacterListPresenter() {
        return injector.characterListPresenter();
    }

    public static CharacterRepository injectCharacterRepository() {
        return injector.characterRepository();
    }
}

