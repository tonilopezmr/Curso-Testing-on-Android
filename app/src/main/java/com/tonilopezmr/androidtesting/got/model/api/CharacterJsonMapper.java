package com.tonilopezmr.androidtesting.got.model.api;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CharacterJsonMapper {

    public GoTCharacter mapper(String json) throws JsonParseException {
        Type listType = new TypeToken<GoTCharacter>() {
        }.getType();
        return new Gson().fromJson(json, listType);
    }

    public List<GoTCharacter> mapperList(String json) throws JsonParseException {
        Type listType = new TypeToken<ArrayList<GoTCharacter>>() {
        }.getType();
        return new Gson().fromJson(json, listType);
    }
}
