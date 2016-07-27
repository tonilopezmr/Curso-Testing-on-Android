package com.tonilopezmr.androidtesting.got.model.api;

import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.List;

public class CharacterApi {

    private String endPoint;
    private CharacterJsonMapper jsonMapper;
    private OkHttpClient client;

    public CharacterApi(String endPoint, CharacterJsonMapper jsonMapper) {
        this.endPoint = endPoint;
        this.jsonMapper = jsonMapper;
        this.client = new OkHttpClient.Builder()
                        .addInterceptor(new JsonHeaderInterceptor())
                        .build();
    }

    public List<GoTCharacter> getAll() throws Exception {
        StringBuffer response = getCharacters();
        return jsonMapper.mapperList(response.toString());
    }

    protected StringBuffer getCharacters() throws Exception {
        Thread.sleep(1200); //fake wait

        Request request = new Request.Builder()
                .url(endPoint)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        return new StringBuffer(response.body().string());
    }

}
