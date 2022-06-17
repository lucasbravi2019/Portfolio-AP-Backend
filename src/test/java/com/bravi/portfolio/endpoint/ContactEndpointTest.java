package com.bravi.portfolio.endpoint;

import com.bravi.portfolio.dto.ContactRequest;
import com.bravi.portfolio.dto.PersonaRequest;
import com.bravi.portfolio.dto.UserRequest;
import com.bravi.portfolio.dto.UserResponse;
import com.bravi.portfolio.entity.Contact;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.util.BuilderUtil;
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
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ContactEndpointTest {

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
    void shouldGetContactList() throws Exception {
        //Given
        ContactRequest contactRequest = BuilderUtil.buildContactRequest(null);

        ResultActions resultActions = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(contactRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        //When
        ResultActions result = mockMvc.perform(get(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        result.andExpect(status().isOk());

    }

    @Test
    void shouldNotGetContactList() throws Exception {
        //Given 0 contacts

        //When
        ResultActions resultActions = mockMvc.perform(get(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldGetContact() throws Exception {
        //Given
        ContactRequest contactRequest = BuilderUtil.buildContactRequest(null);
        ResultActions create = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(contactRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        //When
        ResultActions get = mockMvc.perform(get(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        get.andExpect(status().isOk());
        get.andExpect(jsonPath("$.length()").value(1));
        checkContactResponseArrayFieldByField(get);
    }

    @Test
    void shouldNotGetContactByWrongId() throws Exception {
        //Given
        ContactRequest contactRequest = BuilderUtil.buildContactRequest(null);
        ResultActions create = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(contactRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Contact contact = deserializeJsonString(json, Contact.class);

        //When
        ResultActions get = mockMvc.perform(get(PathName.CONTACT + PathName.PATH_ID, contact.getId() + 1)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        get.andExpect(status().isInternalServerError());
    }

    @Test
    void shouldCreateContact() throws Exception {
        //Given
        ContactRequest contactRequest = BuilderUtil.buildContactRequest(null);

        //When
        ResultActions resultActions = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(contactRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isCreated());
        checkContactResponseFieldByField(resultActions);
    }

    @Test
    void shouldNotCreateContactWithEmptyBody() throws Exception {
        //Given
        ContactRequest contactRequest = ContactRequest.builder().build();

        //When
        ResultActions resultActions = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(contactRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.errors.length()").value(2));
    }

    @Test
    void shouldUpdateContact() throws Exception {
        //Given
        ContactRequest requestCreate = ContactRequest.builder()
                .description("Descripcion nueva")
                .contact("Contacto nuevo")
                .build();

        ContactRequest requestUpdate = BuilderUtil.buildContactRequest(null);

        ResultActions create = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(requestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Contact contact = deserializeJsonString(json, Contact.class);

        //When
        ResultActions update = mockMvc.perform(put(PathName.CONTACT + PathName.PATH_ID, contact.getId())
                .header("Authorization", authorizationHeader)
                .content(asJsonString(requestUpdate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        update.andExpect(status().isOk());
        checkContactResponseFieldByField(update);
    }

    @Test
    void shouldNotUpdateContactByWrongId() throws Exception {
        //Given
        ContactRequest requestCreate = ContactRequest.builder()
                .description("Descripcion nueva")
                .contact("Contacto nuevo")
                .build();

        ContactRequest requestUpdate = BuilderUtil.buildContactRequest(null);

        ResultActions create = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(requestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Contact contact = deserializeJsonString(json, Contact.class);

        //When
        ResultActions update = mockMvc.perform(put(PathName.CONTACT + PathName.PATH_ID, contact.getId() + 1)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(requestUpdate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        update.andExpect(status().isInternalServerError());
    }

    @Test
    void shouldNotUpdateContactWithEmptyBody() throws Exception {
        //Given
        ContactRequest requestCreate = ContactRequest.builder()
                .description("Descripcion nueva")
                .contact("Contacto nuevo")
                .build();

        ContactRequest requestUpdate = ContactRequest.builder().build();

        ResultActions create = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(requestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Contact contact = deserializeJsonString(json, Contact.class);

        //When
        ResultActions update = mockMvc.perform(put(PathName.CONTACT + PathName.PATH_ID, contact.getId())
                .header("Authorization", authorizationHeader)
                .content(asJsonString(requestUpdate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        update.andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteContact() throws Exception {
        //Given
        ContactRequest contactRequest = BuilderUtil.buildContactRequest(null);
        ResultActions create = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(contactRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Contact contact = deserializeJsonString(json, Contact.class);

        //When
        ResultActions delete = mockMvc.perform(delete(PathName.CONTACT + PathName.PATH_ID, contact.getId())
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        delete.andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteContact() throws Exception {
        //Given
        ContactRequest contactRequest = BuilderUtil.buildContactRequest(null);
        ResultActions create = mockMvc.perform(post(PathName.CONTACT)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(contactRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Contact contact = deserializeJsonString(json, Contact.class);

        //When
        ResultActions delete = mockMvc.perform(delete(PathName.CONTACT + PathName.PATH_ID, contact.getId() + 1)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        delete.andExpect(status().isInternalServerError());
    }

    private void checkContactResponseArrayFieldByField(ResultActions resultActions) throws Exception {
        resultActions.andExpect(jsonPath("$.[0].description").value("Description"));
        resultActions.andExpect(jsonPath("$.[0].contact").value("Contact"));
        resultActions.andExpect(jsonPath("$.[0].contact").value("Contact"));
        resultActions.andExpect(jsonPath("$.[0].createdAt").isNotEmpty());
        resultActions.andExpect(jsonPath("$.[0].updatedAt").isNotEmpty());
    }

    private void checkContactResponseFieldByField(ResultActions resultActions) throws Exception {
        resultActions.andExpect(jsonPath("$.description").value("Description"));
        resultActions.andExpect(jsonPath("$.contact").value("Contact"));
        resultActions.andExpect(jsonPath("$.contact").value("Contact"));
        resultActions.andExpect(jsonPath("$.createdAt").isNotEmpty());
        resultActions.andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

}
