package com.tonilopezmr.androidtesting.got.model.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import com.tonilopezmr.androidtesting.got.model.api.exceptions.ItemNotFoundException;
import com.tonilopezmr.androidtesting.got.model.api.exceptions.UnknownErrorException;
import okhttp3.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CharacterApi {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final String ALL = "all";
    public static final String BY_HOUSE = "house";
    public static final String CREATE = "create";

    private OkHttpClient client;
    private String endPoint;

    public CharacterApi(String endPoint) {
        this.endPoint = endPoint;
        this.client = new OkHttpClient.Builder()
                        .addInterceptor(new JsonHeaderInterceptor())
                        .build();
    }

    public List<GoTCharacter> getAll() throws Exception {
        String response = getCharacters(endPoint + ALL);

        Type listType = new TypeToken<ArrayList<GoTCharacter>>() {
        }.getType();
        return new Gson().fromJson(response, listType);
    }

    public List<GoTCharacter> getByHouse(String house) throws Exception {
        String response = getCharacters(endPoint + BY_HOUSE + "/" + house);

        Type listType = new TypeToken<ArrayList<GoTCharacter>>() {
        }.getType();
        return new Gson().fromJson(response, listType);
    }

    public void create(GoTCharacter goTCharacter) throws Exception {
        String json = new Gson().toJson(goTCharacter);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(endPoint)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        inspectResponseForErrors(response);
    }

    private String getCharacters(String endPoint) throws Exception {
        Request request = new Request.Builder()
                .url(endPoint)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        inspectResponseForErrors(response);
        return new StringBuffer(response.body().string()).toString();
    }

    private void inspectResponseForErrors(Response response) throws Exception {
        int code = response.code();
        if (code == 404) {
            throw new ItemNotFoundException();
        } else if (code >= 400) {
            throw new UnknownErrorException(code);
        }
    }

}
