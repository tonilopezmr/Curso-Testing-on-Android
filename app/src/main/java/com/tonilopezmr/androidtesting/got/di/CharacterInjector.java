package com.tonilopezmr.androidtesting.got.di;

import com.tonilopezmr.androidtesting.got.model.CharacterRepository;
import com.tonilopezmr.androidtesting.got.model.api.CharacterApi;
import com.tonilopezmr.androidtesting.got.model.api.CharacterJsonMapper;
import com.tonilopezmr.androidtesting.got.model.validator.CharacterValidator;
import com.tonilopezmr.androidtesting.got.presenter.CharacterListPresenter;

public class CharacterInjector {

    private static CharacterInjector injector;

    private CharacterListPresenter listPresenter;
    private CharacterRepository characterRepository;
    private CharacterValidator characterValidator;
    private CharacterApi characterApi;
    private String endPoint;
    private CharacterJsonMapper characterJsonMapper;

    private String endPoint() {
        if (endPoint == null) {
            return "https://raw.githubusercontent.com/tonilopezmr/Game-of-Thrones/master/app/src/test/resources/data.json";
        }

        return endPoint;
    }

    private CharacterJsonMapper characterJsonMapper() {
        if (characterJsonMapper == null) {
            return new CharacterJsonMapper();
        }

        return characterJsonMapper;
    }

    private CharacterApi characterApi() {
        if (characterApi == null) {
            return new CharacterApi(endPoint(), characterJsonMapper());
        }

        return characterApi;
    }

    private CharacterValidator characterValidator() {
        if (characterValidator == null) {
            return new CharacterValidator();
        }

        return characterValidator;
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

    private void configService(String endPoint) {
        this.endPoint = endPoint;
    }

    private void configService(CharacterJsonMapper characterJsonMapper) {
        this.characterJsonMapper = characterJsonMapper;
    }

    private void configService(CharacterApi characterApi) {
        this.characterApi = characterApi;
    }

    private void configService(CharacterValidator characterValidator) {
        this.characterValidator = characterValidator;
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

    public static void config(CharacterJsonMapper characterJsonMapper) {
        injector.configService(characterJsonMapper);
    }

    public static void config(String endPoint) {
        injector.configService(endPoint);
    }

    public static CharacterListPresenter injectCharacterListPresenter() {
        return injector.characterListPresenter();
    }

    public static CharacterRepository injectCharacterRepository() {
        return injector.characterRepository();
    }
}

