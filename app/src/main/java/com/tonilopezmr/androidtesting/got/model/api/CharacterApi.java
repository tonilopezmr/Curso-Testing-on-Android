package com.tonilopezmr.androidtesting.got.model.api;

import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import com.tonilopezmr.androidtesting.got.model.api.exceptions.ItemNotFoundException;
import com.tonilopezmr.androidtesting.got.model.api.exceptions.UnknownErrorException;
import okhttp3.*;

import java.util.List;

public class CharacterApi {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final String ALL = "all";
    public static final String BY_HOUSE = "house";
    public static final String CREATE = "create";

    private String endPoint;
    private CharacterJsonMapper jsonMapper;
    private OkHttpClient client;

    public CharacterApi(String endPoint, CharacterJsonMapper jsonMapper) {
        this.endPoint = endPoint; //Termina en / no hace falta añadir /all, etc..
        this.jsonMapper = jsonMapper;
        this.client = new OkHttpClient.Builder()
                        .addInterceptor(new JsonHeaderInterceptor())
                        .build();
    }

    public List<GoTCharacter> getAll() throws Exception {
        String response = getCharacters(endPoint + ALL);

        return jsonMapper.mapperList(response);
    }

    public List<GoTCharacter> getByHouse(String house) throws Exception {
        String response = getCharacters(endPoint + BY_HOUSE + "/" + house);

        return jsonMapper.mapperList(response);
    }

    public void create(GoTCharacter goTCharacter) throws Exception {
        String json = gotCharacterJson(goTCharacter);
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(endPoint + CharacterApi.CREATE)
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

    private String gotCharacterJson(GoTCharacter goTCharacter) {
        return "{" +
                "'name':'"+goTCharacter.getName()+"'," +
                "'imageUrl':'"+goTCharacter.getImageUrl()+"'," +
                "'description':'"+goTCharacter.getDescription()+"'," +
                "'houseName':'"+goTCharacter.getHouseName()+"'" +
                "}";
    }

}
