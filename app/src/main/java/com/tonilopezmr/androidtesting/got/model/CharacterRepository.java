package com.tonilopezmr.androidtesting.got.model;

import com.tonilopezmr.androidtesting.got.model.api.CharacterApi;
import com.tonilopezmr.androidtesting.got.model.validator.CharacterValidator;
import com.tonilopezmr.androidtesting.got.model.validator.InvalidException;
import com.tonilopezmr.androidtesting.got.model.validator.Validator;

import java.util.*;


public class CharacterRepository {

    private CharacterValidator characterValidator;
    private LinkedList<GoTCharacter> goTCharacterList;
    private CharacterApi characterApi;

    public CharacterRepository(CharacterValidator characterValidator, CharacterApi characterApi) {
        this.characterValidator = characterValidator;
        this.characterApi = characterApi;
        this.goTCharacterList = new LinkedList<>();
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
        List<GoTCharacter> characterList = characterApi.getAll();
        characterList.addAll(goTCharacterList);
        return characterList;
    }


    public List<GoTCharacter> getAllByHouse(String houseName) throws Exception {
        List<GoTCharacter> characterList = getAll();

        Iterator<GoTCharacter> iterator = characterList.iterator();
        while (iterator.hasNext()) {
            GoTCharacter goTCharacter = iterator.next();
            if (!goTCharacter.getHouseName().equals(houseName)){
                iterator.remove();
            }
        }

        return characterList;
    }

    public List<GoTCharacter> getSortByName() throws Exception {
        List<GoTCharacter> characters = getAll();
        Collections.sort(characters, new Comparator<GoTCharacter>() {
            @Override
            public int compare(GoTCharacter character, GoTCharacter t1) {
                return character.getName().compareTo(t1.getName());
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

}
