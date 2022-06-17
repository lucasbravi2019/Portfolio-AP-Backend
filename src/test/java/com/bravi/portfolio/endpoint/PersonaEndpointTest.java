package com.bravi.portfolio.endpoint;

import com.bravi.portfolio.dto.PersonaRequest;
import com.bravi.portfolio.dto.UserRequest;
import com.bravi.portfolio.dto.UserResponse;
import com.bravi.portfolio.entity.Persona;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.util.BuilderUtil;
import com.bravi.portfolio.util.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

import javax.transaction.Transactional;

import static com.bravi.portfolio.util.TestUtil.asJsonString;
import static com.bravi.portfolio.util.TestUtil.deserializeJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@Slf4j
public class PersonaEndpointTest {

    @Autowired
    MockMvc mockMvc;

    String authorizationHeader;

    @BeforeEach
    void setUp() throws Exception {
        String json = mockMvc.perform(post(PathName.LOGIN)
                .content(asJsonString(UserRequest.builder().username("Lucas").password("password").build()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andReturn().getResponse().getContentAsString();

        UserResponse token = deserializeJsonString(json, UserResponse.class);
        this.authorizationHeader = String.format("Bearer %s", token.getJwt());
    }

    @Test
    void shouldGetPerson() throws Exception {
        //Given
        PersonaRequest persona = PersonaRequest.builder()
                .firstName("Lucas")
                .lastName("Bravi")
                .build();

        log.info(asJsonString(persona));

        ResultActions result = mockMvc.perform(post(PathName.PERSONA)
                .header("Authorization", authorizationHeader)
                .content(TestUtil.asJsonString(persona))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));


        //When
        ResultActions resultActions = mockMvc.perform(get(PathName.PERSONA)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isOk());
        checkPersonaResponse(resultActions, "Lucas", "Bravi");
    }

    @Test
    void shouldNotGetPerson() throws Exception {
        //Given 0 person

        //When
        ResultActions resultActions = mockMvc.perform(get(PathName.PERSONA)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    void shouldCreatePerson() throws Exception {
        //Given
        PersonaRequest personaRequest = BuilderUtil.buildPersonaRequest();

        //When
        ResultActions resultActions = mockMvc.perform(post(PathName.PERSONA)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(personaRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isCreated());
        checkPersonaResponse(resultActions, "Lucas", "Bravi");
    }

    @Test
    void shouldNotCreatePersonWithEmptyBody() throws Exception {
        //Given
        PersonaRequest personaRequest = PersonaRequest.builder().build();

        //When
        ResultActions resultActions = mockMvc.perform(post(PathName.PERSONA)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(personaRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.errors").exists());
        resultActions.andExpect(jsonPath("$.errors").isNotEmpty());
        resultActions.andExpect(jsonPath("$.errors.length()").value(2));
    }

    @Test
    void shouldNotCreatePersonIfExists() throws Exception {
        //Given
        PersonaRequest personaRequest = PersonaRequest.builder()
                .firstName("Lucas")
                .lastName("Bravi")
                .build();

        ResultActions resultActions = mockMvc.perform(post(PathName.PERSONA)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(personaRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //When
        ResultActions result = mockMvc.perform(post(PathName.PERSONA)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(personaRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        result.andExpect(status().isInternalServerError());
    }

    @Test
    void shouldUpdatePersona() throws Exception {
        //Given
        PersonaRequest personaCreate = PersonaRequest.builder()
                .firstName("Lucas")
                .lastName("Bravi")
                .build();

        PersonaRequest personaUpdate = PersonaRequest.builder()
                .firstName("Juan")
                .lastName("Gonzales")
                .build();

        ResultActions resultActions = mockMvc.perform(post(PathName.PERSONA)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(personaCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        String json = resultActions.andReturn().getResponse().getContentAsString();
        Persona persona = TestUtil.deserializeJsonString(json, Persona.class);

        //When
        ResultActions update = mockMvc.perform(put(PathName.PERSONA + PathName.PATH_ID, persona.getId())
                .header("Authorization", authorizationHeader)
                .content(asJsonString(personaUpdate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        update.andExpect(status().isOk());
        checkPersonaResponse(update, "Juan", "Gonzales");
    }

    private void checkPersonaResponse(ResultActions resultActions, String firstName, String lastName) throws Exception {
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.firstName").value(firstName));
        resultActions.andExpect(jsonPath("$.lastName").value(lastName));
        resultActions.andExpect(jsonPath("$.createdAt").isNotEmpty());
        resultActions.andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

}
