package com.tonilopezmr.androidtesting.got.model.api;

import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CharacterApiTest extends MockWebServerTest {

    private CharacterApi api;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        String mockEndPoint = getBaseEndpoint();
        api = new CharacterApi(mockEndPoint, new CharacterJsonMapper());
    }

    @Test
    public void
    sends_the_correct_path_when_calls_get_all() throws Exception {
        enqueueMockResponse();

        api.getAll();

        assertGetRequestSentTo("/"+ CharacterApi.ALL);
    }

    @Test
    public void
    sends_the_correct_character_body_when_create() throws Exception {
        enqueueMockResponse();
        GoTCharacter goTCharacter = new GoTCharacter("pepe", "", "des", "house_name");

        api.create(goTCharacter);

        assertRequestBodyEquals(gotCharacterJson(goTCharacter));
    }

    @Test
    public void
    parse_ten_characters_when_call_get_all() throws Exception {
        GoTCharacter goTCharacter = new GoTCharacter("pepe", "", "des", "house_name");
        String body = "["+ gotCharacterJson(goTCharacter)
                + "," + gotCharacterJson(goTCharacter)
                + "," + gotCharacterJson(goTCharacter)
                + "]";
        enqueueMockResponse(body);

        List<GoTCharacter> goTCharacterList = api.getAll();

        assertThat(goTCharacterList.size(), is(3));
        assertExpectedCharacter(goTCharacterList.get(0), goTCharacter);
    }

    private void assertExpectedCharacter(GoTCharacter goTCharacter, GoTCharacter expected) {
        assertThat(goTCharacter.getName(), is(expected.getName()));
        assertThat(goTCharacter.getDescription(), is(expected.getDescription()));
        assertThat(goTCharacter.getImageUrl(), is(expected.getImageUrl()));
        assertThat(goTCharacter.getHouseName(), is(expected.getHouseName()));
    }

    @Test
    public void
    sends_accept_header_when_api_calls() throws Exception {
        enqueueMockResponse();

        api.getAll();

        assertRequestContainsHeader("Accept", "application/json");
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