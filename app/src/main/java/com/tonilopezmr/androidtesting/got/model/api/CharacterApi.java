package com.tonilopezmr.androidtesting.got.model.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CharacterApi {

    public List<GoTCharacter> getAll() throws Exception {
        StringBuffer response = getCharacters();

        Type listType = new TypeToken<ArrayList<GoTCharacter>>() {
        }.getType();
        return new Gson().fromJson(response.toString(), listType);
    }

    private StringBuffer getCharacters() throws Exception {
        Thread.sleep(1200); //fake wait
        String endPoint =
                "https://raw.githubusercontent.com/tonilopezmr/Game-of-Thrones/master/app/src/test/resources/data.json";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(endPoint)
                .build();

        Response response = client.newCall(request).execute();
        return new StringBuffer(response.body().string());
    }

}
