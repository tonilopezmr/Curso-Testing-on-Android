package com.tonilopezmr.androidtesting.got.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tonilopezmr.androidtesting.got.model.validator.CharacterValidator;
import com.tonilopezmr.androidtesting.got.model.validator.InvalidException;
import com.tonilopezmr.androidtesting.got.model.validator.Validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class CharacterRepository {

    private CharacterValidator characterValidator;
    private LinkedList<GoTCharacter> goTCharacterList;

    public CharacterRepository(CharacterValidator characterValidator) {
        this.characterValidator = characterValidator;
        goTCharacterList = new LinkedList<>();
    }

    /**
     * Debe devolver una lista de personajes de juego de tronos de internet <b>junto
     * con el contenido en la lista {@link #goTCharacterList} </b>
     * <p>
     * <b>Escribir los test para este metodo, si el metodo no cumple su fin,
     * terminarlo para que lo haga</b>
     *
     * @return Lista de personajes de juego de tronos
     * @throws Exception
     */
    public List<GoTCharacter> getAll() throws Exception {
        StringBuffer response = getCharactersFromUrl();

        Type listType = new TypeToken<ArrayList<GoTCharacter>>() {
        }.getType();
        return new Gson().fromJson(response.toString(), listType);
    }


    public List<GoTCharacter> getAllByHouse(String houseName) throws Exception {
        return getAll();
    }

    public List<GoTCharacter> sortByName() throws Exception {
        List<GoTCharacter> characters = getAll();
        Collections.sort(characters, new Comparator<GoTCharacter>() {
            @Override
            public int compare(GoTCharacter character, GoTCharacter t1) {
                return character.getName().compareTo(t1.getHouseName());
            }
        });

        return characters;
    }

    /**
     *
     * Debe añadir a la lista el nuevo personaje que se le pasa por parametros.
     *
     * @param goTCharacter
     * @throws InvalidException Si no el personaje no es valido
     */
    public void create(GoTCharacter goTCharacter) throws InvalidException {
        Validator validator = characterValidator.valid(goTCharacter);
        if (!validator.isValid()) {
            throw new InvalidException(validator);
        }

        goTCharacterList.add(goTCharacter);
    }

    protected StringBuffer getCharactersFromUrl() throws Exception {
        Thread.sleep(1200); //fake wait

        String url =
                "https://raw.githubusercontent.com/tonilopezmr/Game-of-Thrones/master/app/src/test/resources/data.json";
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

    public StringBuffer getContentFromFile(String filePath) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filePath);
        File file = new File(resource.getPath());
        return converterFileToString(file);
    }

    private StringBuffer converterFileToString(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb;
    }
}
