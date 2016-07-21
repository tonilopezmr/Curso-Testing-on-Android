package com.tonilopezmr.androidtesting.got.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CharacterCollection {

    public List<GoTCharacter> getCharacters() throws Exception {
        StringBuffer response = getCharactersFromUrl();

        Type listType = new TypeToken<ArrayList<GoTCharacter>>() {}.getType();
        return new Gson().fromJson(response.toString(), listType);
    }

    protected StringBuffer getCharactersFromUrl() throws Exception {
        Thread.sleep(1200); //fake wait

        String url = "https://raw.githubusercontent.com/tonilopezmr/Game-of-Thrones/master/app/src/test/resources/data.json";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;
    }

    public StringBuffer getContentFromFile(String filePath) throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filePath);
        File file = new File(resource.getPath());
        return converterFileToString(file);
    }

    private StringBuffer converterFileToString(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null){
            sb.append(line).append("\n");
        }
        reader.close();
        return sb;
    }
}
