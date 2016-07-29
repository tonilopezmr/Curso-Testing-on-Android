package com.tonilopezmr.androidtesting.got.model.api;

import com.tonilopezmr.androidtesting.got.model.GoTCharacter;

public class CharacterApiTest extends MockWebServerTest {


    /**
     * Json que nos devuelve el servidor por un previo acuerdo con los chicos del backend.
     *
     * @param goTCharacter
     * @return
     */
    private String gotCharacterJson(GoTCharacter goTCharacter) {
        return "{" +
                "\"description\":\""+goTCharacter.getDescription()+"\"," +
                "\"houseName\":\""+goTCharacter.getHouseName()+"\"," +
                "\"imageUrl\":\""+goTCharacter.getImageUrl()+"\"," +
                "\"name\":\""+goTCharacter.getName()+"\"" +
                "}";
    }
}