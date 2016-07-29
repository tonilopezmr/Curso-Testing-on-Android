package com.tonilopezmr.androidtesting.got.model.api;

import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import com.tonilopezmr.androidtesting.got.model.api.exceptions.UnknownErrorException;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class CharacterApiTest extends MockWebServerTest {

    private CharacterApi api;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        String mockEndPoint = getBaseEndpoint();
        api = new CharacterApi(mockEndPoint, new CharacterJsonMapper());

        //También se puede hacer esto usando el injector
//        CharacterInjector.load(new CharacterInjector()); //Inicializamos el injector
//        CharacterInjector.config(mockEndPoint);         //Sustituimos el endPoint de producción por el de test
//        //Injectamos la Api con su CharacterJsonMapper real (por que no lo hemos sustituido por uno ficticio)
//        //Y su endPoint de test
//        api = CharacterInjector.injectCharacterApi();
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
    sends_the_correct_path_when_calls_get_by_house() throws Exception {
        enqueueMockResponse();

        api.getByHouse("Lannister");

        assertGetRequestSentTo("/"+ CharacterApi.BY_HOUSE + "/Lannister");
    }

    @Test
    public void
    sends_the_correct_path_when_calls_create() throws Exception {
        enqueueMockResponse();

        api.create(new GoTCharacter("", "", "", ""));

        assertPostRequestSentTo("/"+ CharacterApi.CREATE);
    }


    @Test
    public void
    sends_the_correct_character_body_when_create() throws Exception {
        enqueueMockResponse();
        GoTCharacter goTCharacter = new GoTCharacter("pepe", "", "des", "house_name");

        api.create(goTCharacter);

        assertRequestBodyEquals(gotCharacterJson(goTCharacter));
    }

    @Test(expected = UnknownErrorException.class)
    public void
    throw_exception_when_there_is_not_internet() throws Exception {
        enqueueMockResponse(503);

        api.getAll();
    }

    @Test(expected = UnknownErrorException.class)
    public void
    throw_exception_when_there_are_any_server_problem_in_create() throws Exception {
        enqueueMockResponse(501);

        api.create(new GoTCharacter("", "", "", ""));
    }

    @Test
    public void
    parse_three_characters_when_call_get_all() throws Exception {
        GoTCharacter goTCharacter = new GoTCharacter("pepe", "", "des", "house_name");
        String body = "["+ gotCharacterJson(goTCharacter)
                + "," + gotCharacterJson(goTCharacter)
                + "," + gotCharacterJson(goTCharacter)
                + "]";
        enqueueMockResponse(body);

        List<GoTCharacter> goTCharacterList = api.getAll();

        assertThat(goTCharacterList.size(), is(3));
        assertExpectedCharacter(goTCharacterList.get(0), goTCharacter);  //Método para comprobar un character  |
        assertThat(goTCharacterList.get(0), isCharacter(goTCharacter));  //assertThat con un Custom Matcher    |
    }

    /**
     * Este es el poder hamcrest de hacernos nuestros propios matches,
     * para comprobar que un Personaje es el que deseamos.
     *
     *
     * @param goTCharacter Personaje de juego de tronos
     * @return
     */
    private Matcher<? super GoTCharacter> isCharacter(final GoTCharacter goTCharacter) {
        return new CustomTypeSafeMatcher<GoTCharacter>(goTCharacter.toString()) {
            @Override
            protected boolean matchesSafely(GoTCharacter item) {
                return item.equals(goTCharacter);
            }
        };
    }

    /**
     * Como el comparador Equals está implementado en {@link GoTCharacter} no hace falta assertar así.
     *
     * Así sería suficiente:
     *
     *       assertTrue(goTCharacter.equals(expected));
     *
     * @param goTCharacter
     * @param expected
     */
    private void assertExpectedCharacter(GoTCharacter goTCharacter, GoTCharacter expected) {
        assertTrue(goTCharacter.equals(expected));
        /*assertThat(goTCharacter.getName(), is(expected.getName()));
        assertThat(goTCharacter.getDescription(), is(expected.getDescription()));
        assertThat(goTCharacter.getImageUrl(), is(expected.getImageUrl()));
        assertThat(goTCharacter.getHouseName(), is(expected.getHouseName()));*/
    }

    @Test
    public void
    sends_accept_header_when_api_calls() throws Exception {
        enqueueMockResponse();

        api.getAll();

        assertRequestContainsHeader("Accept", "application/json");
    }

    @Test
    public void
    sends_content_type_header_when_api_calls() throws Exception {
        enqueueMockResponse();

        api.getAll();

        assertRequestContainsHeader("Content-Type", "application/json");
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