package com.tonilopezmr.androidtesting.got.model;

import android.support.annotation.NonNull;
import com.tonilopezmr.androidtesting.got.model.api.CharacterApi;
import com.tonilopezmr.androidtesting.got.model.validator.CharacterValidator;
import com.tonilopezmr.androidtesting.got.model.validator.Validator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharacterRepositoryTest {

    @Test
    public void
    return_characters_by_house_name() throws Exception {
        GoTCharacter pepe = getCharacter("Pepe", "Stark");
        GoTCharacter lucia = getCharacter("Lucia", "Stark");
        GoTCharacter ana = getCharacter("Ana", "Lannister");
        GoTCharacter sara = getCharacter("Sara", "");

        CharacterRepository characterRepository = new CharacterRepositoryBuilder()
                .withCharacters(Arrays.asList(pepe, lucia, ana, sara))
                .build();

        List<GoTCharacter> charactersByStark = characterRepository.getAllByHouse("Stark");

        assertThat(charactersByStark, containsInAnyOrder(lucia, pepe));
    }

    @Test
    public void
    sort_characters() throws Exception {
        GoTCharacter pepe = getCharacterNamed("Pepe");
        GoTCharacter lucia = getCharacterNamed("Lucia");
        GoTCharacter ana = getCharacterNamed("Ana");
        GoTCharacter sara = getCharacterNamed("Sara");

        CharacterRepository characterRepository = new CharacterRepositoryBuilder()
                .withCharacters(Arrays.asList(pepe, lucia, ana, sara))
                .build();

        List<GoTCharacter> sortedCharacters = characterRepository.getSortByName();

        assertThat(sortedCharacters, contains(ana, lucia, pepe, sara));
    }

    @Test
    public void
    return_one_additional_character_when_create() throws Exception {
        GoTCharacter dummy = mock(GoTCharacter.class);
        CharacterRepository characterRepository = new CharacterRepositoryBuilder()
                .withoutCharacters()
                .characterValidatorAlways(true, Arrays.asList(dummy))
                .build();

        characterRepository.create(dummy);

        List<GoTCharacter> characterList = characterRepository.getAll();
        assertThat(characterList, contains(dummy));
    }

    @Test
    public void
    sort_characters_with_create_an_additional_one() throws Exception {
        GoTCharacter pepe = getCharacterNamed("Pepe");
        GoTCharacter lucia = getCharacterNamed("Lucia");
        GoTCharacter ana = getCharacterNamed("Ana");

        CharacterRepository characterRepository = new CharacterRepositoryBuilder()
                .withCharacters(Arrays.asList(pepe, lucia))
                .characterValidatorAlways(true, Arrays.asList(pepe, lucia, ana))
                .build();

        characterRepository.create(ana);

        List<GoTCharacter> characterList = characterRepository.getSortByName();
        assertThat(characterList, contains(ana, lucia, pepe));
    }

    @NonNull
    private GoTCharacter getCharacterNamed(String name) {
        GoTCharacter character = mock(GoTCharacter.class);
        when(character.getName()).thenReturn(name);
        when(character.toString()).thenReturn(name);
        return character;
    }

    @NonNull
    private GoTCharacter getCharacter(String pepe, String house) {
        GoTCharacter character = getCharacterNamed(pepe);
        when(character.getHouseName()).thenReturn(house);
        return character;
    }


    private class CharacterRepositoryBuilder {

        private CharacterValidator characterValidator;
        private CharacterApi characterApi;

        CharacterRepositoryBuilder() {
            this.characterValidator = mock(CharacterValidator.class);
            this.characterApi = mock(CharacterApi.class);
        }

        CharacterRepositoryBuilder characterValidatorAlways(boolean isValid, List<GoTCharacter> dummies) {
            Validator validator = mock(Validator.class);
            for (GoTCharacter character : dummies) {
                when(characterValidator.valid(character)).thenReturn(validator);
            }
            when(validator.isValid()).thenReturn(isValid);
            return this;
        }

        CharacterRepositoryBuilder withoutCharacters() throws Exception {
            when(this.characterApi.getAll()).thenReturn(new ArrayList<GoTCharacter>());
            return this;
        }

        CharacterRepositoryBuilder withCharacters(List<GoTCharacter> characters) throws Exception {
            when(this.characterApi.getAll()).thenReturn(new ArrayList(characters));
            return this;
        }

        CharacterRepository build() {
            return new CharacterRepository(this.characterValidator, this.characterApi);
        }
    }
}