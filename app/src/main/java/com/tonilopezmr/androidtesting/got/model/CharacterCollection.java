package com.tonilopezmr.androidtesting.got.model;

import java.util.Collections;
import java.util.List;

public class CharacterCollection {

    public void getCharacters(Callback callback) {
        callback.success(Collections.<GoTCharacter>emptyList());
    }

    public interface Callback {
        void success(List<GoTCharacter> characterList);
        void error(Exception ex);
    }
}
