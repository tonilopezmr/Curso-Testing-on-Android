package com.tonilopezmr.androidtesting.got.view;

import com.tonilopezmr.androidtesting.got.MVP;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;

import java.util.List;

public interface CharacterListView extends MVP.View {

    void show(List<GoTCharacter> characterList);
}
