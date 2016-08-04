package com.tonilopezmr.androidtesting.got.di;

import com.tonilopezmr.androidtesting.got.model.CharacterRepository;
import com.tonilopezmr.androidtesting.got.model.api.CharacterApi;
import com.tonilopezmr.androidtesting.got.model.validator.CharacterValidator;

/**
 * CharacterInjector with Service Locator.
 *
 * http://martinfowler.com/articles/injection.html
 *
 */
public class CharacterInjector {

    private static CharacterInjector injector;

    private CharacterRepository characterRepository;
    private CharacterValidator characterValidator;
    private CharacterApi characterApi;
    private String endPoint;

    private String endPoint() {
        if (endPoint == null) {
            return "https://raw.githubusercontent.com/tonilopezmr/Game-of-Thrones/master/app/src/test/resources/data.json";
        }

        return endPoint;
    }

    private CharacterApi characterApi() {
        if (characterApi == null) {
            return new CharacterApi(endPoint());
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


    private void configService(String endPoint) {
        this.endPoint = endPoint;
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


    /////////////////////////////
    ////      Public API     ////
    /////////////////////////////

    public static void load(CharacterInjector injectorLocator) {
        injector = injectorLocator;
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

    public static void config(String endPoint) {
        injector.configService(endPoint);
    }

    public static CharacterRepository injectCharacterRepository() {
        return injector.characterRepository();
    }
}

