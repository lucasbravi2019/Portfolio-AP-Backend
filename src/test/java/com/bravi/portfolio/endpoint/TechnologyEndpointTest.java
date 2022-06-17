package com.bravi.portfolio.endpoint;

import com.bravi.portfolio.dto.TechnologyRequest;
import com.bravi.portfolio.dto.UserRequest;
import com.bravi.portfolio.dto.UserResponse;
import com.bravi.portfolio.entity.Technology;
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
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class TechnologyEndpointTest {

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
    void shouldGetAllTechnologies() throws Exception {
        //Given
        ResultActions create = createTechnology(null);

        //When
        ResultActions get = mockMvc.perform(get(PathName.TECHNOLOGY)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        get.andExpect(status().isOk());
        get.andExpect(jsonPath("$.length()").value(1));
        checkTechnologyResponseArrayFieldByField(get);
    }

    @Test
    void shouldNotGetAllTechnologies() throws Exception {
        //Given 0 Technologies

        //When
        ResultActions get = mockMvc.perform(get(PathName.TECHNOLOGY)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        get.andExpect(status().isOk());
        get.andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldGetTechnologyById() throws Exception {
        //Given
        ResultActions create = createTechnology(null);
        String json = create.andReturn().getResponse().getContentAsString();
        Technology technology = deserializeJsonString(json, Technology.class);

        //When
        ResultActions get = mockMvc.perform(get(PathName.TECHNOLOGY + PathName.PATH_ID, technology.getId())
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        get.andExpect(status().isOk());
        checkTechnologyResponseFieldByField(get);
    }

    @Test
    void shouldNotGetTechnologyByWrongId() throws Exception {
        //Given
        ResultActions create = createTechnology(null);
        String json = create.andReturn().getResponse().getContentAsString();
        Technology technology = deserializeJsonString(json, Technology.class);

        //When
        ResultActions get = mockMvc.perform(get(PathName.TECHNOLOGY + PathName.PATH_ID, technology.getId() + 1)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        get.andExpect(status().isInternalServerError());
    }

    @Test
    void shouldCreateTechnology() throws Exception {
        //Given
        TechnologyRequest technologyRequest = BuilderUtil.buildTechnologyRequest(null);

        //When
        ResultActions post = mockMvc.perform(post(PathName.TECHNOLOGY)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(technologyRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        post.andExpect(status().isCreated());
        checkTechnologyResponseFieldByField(post);
    }

    @Test
    void shouldNotCreateTechnologyWithEmptyBody() throws Exception {
        //Given
        TechnologyRequest technologyRequest = TechnologyRequest.builder().build();

        //When
        ResultActions post = mockMvc.perform(post(PathName.TECHNOLOGY)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(technologyRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        post.andExpect(status().isBadRequest());
        post.andExpect(jsonPath("$.errors.length()").value(2));
    }

    @Test
    void shouldUpdateTechnology() throws Exception {
        //Given
        TechnologyRequest createRequest = TechnologyRequest.builder().technologyName("Javascript").technologyLevel(50).build();
        TechnologyRequest updateRequest = BuilderUtil.buildTechnologyRequest(null);
        ResultActions create = createTechnology(createRequest);

        String json = create.andReturn().getResponse().getContentAsString();
        Technology technology = deserializeJsonString(json, Technology.class);

        //When
        ResultActions put = mockMvc.perform(put(PathName.TECHNOLOGY + PathName.PATH_ID, technology.getId())
                .header("Authorization", authorizationHeader)
                .content(asJsonString(updateRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        put.andExpect(status().isOk());
        checkTechnologyResponseFieldByField(put);
    }

    @Test
    void shouldNotUpdateTechnologyWithWrongId() throws Exception {
        //Given
        TechnologyRequest createRequest = TechnologyRequest.builder().technologyName("Javascript").technologyLevel(50).build();
        TechnologyRequest updateRequest = BuilderUtil.buildTechnologyRequest(null);
        ResultActions create = createTechnology(createRequest);

        String json = create.andReturn().getResponse().getContentAsString();
        Technology technology = deserializeJsonString(json, Technology.class);

        //When
        ResultActions put = mockMvc.perform(put(PathName.TECHNOLOGY + PathName.PATH_ID, technology.getId() + 1)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(updateRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        put.andExpect(status().isInternalServerError());
    }


    @Test
    void shouldNotUpdateTechnologyWithEmptyBody() throws Exception {
        //Given
        TechnologyRequest createRequest = TechnologyRequest.builder().technologyName("Javascript").technologyLevel(50).build();
        TechnologyRequest updateRequest = TechnologyRequest.builder().build();
        ResultActions create = createTechnology(createRequest);

        String json = create.andReturn().getResponse().getContentAsString();
        Technology technology = deserializeJsonString(json, Technology.class);

        //When
        ResultActions put = mockMvc.perform(put(PathName.TECHNOLOGY + PathName.PATH_ID, technology.getId())
                .header("Authorization", authorizationHeader)
                .content(asJsonString(updateRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        put.andExpect(status().isBadRequest());
        put.andExpect(jsonPath("$.errors.length()").value(2));
    }

    @Test
    void shouldDeleteTechnology() throws Exception {
        //Given
        ResultActions post = createTechnology(null);
        String json = post.andReturn().getResponse().getContentAsString();
        Technology technology = deserializeJsonString(json, Technology.class);

        //When
        ResultActions delete = mockMvc.perform(delete(PathName.TECHNOLOGY + PathName.PATH_ID, technology.getId())
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        delete.andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteTechnologyByWrongId() throws Exception {
        //Given
        ResultActions post = createTechnology(null);
        String json = post.andReturn().getResponse().getContentAsString();
        Technology technology = deserializeJsonString(json, Technology.class);

        //When
        ResultActions delete = mockMvc.perform(delete(PathName.TECHNOLOGY + PathName.PATH_ID, technology.getId() + 1)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        delete.andExpect(status().isInternalServerError());
    }

    ResultActions createTechnology(TechnologyRequest technologyRequest) throws Exception {
        TechnologyRequest request;
        if (technologyRequest == null) {
            request = BuilderUtil.buildTechnologyRequest(null);
        } else {
            request = technologyRequest;
        }

        ResultActions resultActions = mockMvc.perform(post(PathName.TECHNOLOGY)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(request))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        return resultActions;
    }

    void checkTechnologyResponseArrayFieldByField(ResultActions resultActions) throws Exception {
        resultActions.andExpect(jsonPath("$.[0].id").isNotEmpty());
        resultActions.andExpect(jsonPath("$.[0].technologyName").value("Java"));
        resultActions.andExpect(jsonPath("$.[0].technologyLevel").value(15));
        resultActions.andExpect(jsonPath("$.[0].persona").doesNotExist());
        resultActions.andExpect(jsonPath("$.[0].createdAt").isNotEmpty());
        resultActions.andExpect(jsonPath("$.[0].updatedAt").isNotEmpty());
    }

    void checkTechnologyResponseFieldByField(ResultActions resultActions) throws Exception {
        resultActions.andExpect(jsonPath("$.id").isNotEmpty());
        resultActions.andExpect(jsonPath("$.technologyName").value("Java"));
        resultActions.andExpect(jsonPath("$.technologyLevel").value(15));
        resultActions.andExpect(jsonPath("$.persona").doesNotExist());
        resultActions.andExpect(jsonPath("$.createdAt").isNotEmpty());
        resultActions.andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

}
