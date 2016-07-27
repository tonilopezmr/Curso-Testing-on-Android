package com.tonilopezmr.androidtesting.got.model.api;

import com.tonilopezmr.androidtesting.got.model.GoTCharacter;

public class CharacterApiTest extends MockWebServerTest {


    private String gotCharacterJson(GoTCharacter goTCharacter) {
        return "{" +
                "\"description\":\""+goTCharacter.getDescription()+"\"," +
                "\"houseName\":\""+goTCharacter.getHouseName()+"\"," +
                "\"imageUrl\":\""+goTCharacter.getImageUrl()+"\"," +
                "\"name\":\""+goTCharacter.getName()+"\"" +
                "}";
    }
}