package com.bravi.portfolio.endpoint;

import com.bravi.portfolio.dto.JobRequest;
import com.bravi.portfolio.dto.UserRequest;
import com.bravi.portfolio.dto.UserResponse;
import com.bravi.portfolio.entity.Job;
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

import java.time.LocalDate;

import static com.bravi.portfolio.util.TestUtil.asJsonString;
import static com.bravi.portfolio.util.TestUtil.deserializeJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class JobEndpointTest {

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
    void shouldGetJobList() throws Exception {
        //Given
        JobRequest jobRequest = BuilderUtil.buildJobRequest(null);

        mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        //When
        ResultActions resultActions = mockMvc.perform(get(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.length()").value(1));
        checkJobResponseArrayFieldByField(resultActions);
    }

    @Test
    void shouldNotGetJobList() throws Exception {
        //Given no job

        //When
        ResultActions resultActions = mockMvc.perform(get(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldCreateJob() throws Exception {
        //Given
        JobRequest jobRequest = BuilderUtil.buildJobRequest(null);

        //When
        ResultActions resultActions = mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .content(asJsonString(jobRequest))
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isCreated());
        checkJobResponseFieldByField(resultActions);
    }

    @Test
    void shouldNotCreateJobWithEmptyBody() throws Exception {
        //Given
        JobRequest jobRequest = JobRequest.builder().build();

        //When
        ResultActions resultActions = mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequest))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.errors.length()").value(5));
    }

    @Test
    void shouldUpdateJob() throws Exception {
        //Given
        JobRequest jobRequestCreate = JobRequest.builder()
                .startDate(LocalDate.of(2022, 05, 25))
                .endDate(LocalDate.of(2022, 07, 15))
                .companyName("CompanyName")
                .jobDescription("Job description")
                .jobRole("Job Role")
                .build();

        JobRequest jobRequestUpdate = BuilderUtil.buildJobRequest(null);

        //When
        ResultActions create = mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Job job = deserializeJsonString(json, Job.class);

        ResultActions update = mockMvc.perform(put(PathName.JOB + PathName.PATH_ID, job.getId())
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestUpdate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        update.andExpect(status().isOk());
        checkJobResponseFieldByField(update);
    }

    @Test
    void shouldNotUpdateJobWithWrongId() throws Exception {
        //Given
        JobRequest jobRequestCreate = JobRequest.builder()
                .startDate(LocalDate.of(2022, 05, 25))
                .endDate(LocalDate.of(2022, 07, 15))
                .companyName("CompanyName")
                .jobDescription("Job description")
                .jobRole("Job Role")
                .build();

        JobRequest jobRequestUpdate = BuilderUtil.buildJobRequest(null);

        //When
        ResultActions create = mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Job job = deserializeJsonString(json, Job.class);

        ResultActions update = mockMvc.perform(put(PathName.JOB + PathName.PATH_ID, job.getId() + 1)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestUpdate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        update.andExpect(status().isInternalServerError());
    }

    @Test
    void shouldNotUpdateJobWithEmptyBody() throws Exception {
        //Given
        JobRequest jobRequestCreate = JobRequest.builder()
                .startDate(LocalDate.of(2022, 05, 25))
                .endDate(LocalDate.of(2022, 07, 15))
                .companyName("CompanyName")
                .jobDescription("Job description")
                .jobRole("Job Role")
                .build();

        JobRequest jobRequestUpdate = JobRequest.builder().build();

        //When
        ResultActions create = mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Job job = deserializeJsonString(json, Job.class);

        ResultActions update = mockMvc.perform(put(PathName.JOB + PathName.PATH_ID, job.getId())
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestUpdate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        update.andExpect(status().isBadRequest());
        update.andExpect(jsonPath("$.errors.length()").value(5));
    }

    @Test
    void shouldGetJob() throws Exception {
        //Given
        JobRequest jobRequestCreate = BuilderUtil.buildJobRequest(null);

        //When
        ResultActions create = mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Job job = deserializeJsonString(json, Job.class);

        ResultActions resultAction = mockMvc.perform(get(PathName.JOB + PathName.PATH_ID, job.getId())
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultAction.andExpect(status().isOk());
        checkJobResponseFieldByField(resultAction);
    }

    @Test
    void shouldNotGetJobWithWrongId() throws Exception {
        //Given
        JobRequest jobRequestCreate = BuilderUtil.buildJobRequest(null);

        //When
        ResultActions create = mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Job job = deserializeJsonString(json, Job.class);

        ResultActions resultAction = mockMvc.perform(get(PathName.JOB + PathName.PATH_ID, job.getId() + 1)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultAction.andExpect(status().isInternalServerError());
        resultAction.andExpect(jsonPath("$.errors").exists());
        resultAction.andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void shouldNotDeleteJobWithWrongId() throws Exception {
        //Given no Job
        JobRequest jobRequestCreate = BuilderUtil.buildJobRequest(null);

        //When
        ResultActions create = mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Job job = deserializeJsonString(json, Job.class);

        ResultActions resultAction = mockMvc.perform(delete(PathName.JOB + PathName.PATH_ID, job.getId() + 1)
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultAction.andExpect(status().isInternalServerError());
        resultAction.andExpect(jsonPath("$.errors").exists());
        resultAction.andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void shouldDeleteJob() throws Exception {
        //Given no Job
        JobRequest jobRequestCreate = BuilderUtil.buildJobRequest(null);

        //When
        ResultActions create = mockMvc.perform(post(PathName.JOB)
                .header("Authorization", authorizationHeader)
                .content(asJsonString(jobRequestCreate))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());

        String json = create.andReturn().getResponse().getContentAsString();
        Job job = deserializeJsonString(json, Job.class);

        ResultActions resultAction = mockMvc.perform(delete(PathName.JOB + PathName.PATH_ID, job.getId())
                .header("Authorization", authorizationHeader)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        //Then
        resultAction.andExpect(status().isOk());
    }


    private void checkJobResponseArrayFieldByField(ResultActions resultActions) throws Exception {
        resultActions.andExpect(jsonPath("$.[0].startDate").value("2022-05-18"));
        resultActions.andExpect(jsonPath("$.[0].endDate").value("2022-05-19"));
        resultActions.andExpect(jsonPath("$.[0].companyName").value("Company"));
        resultActions.andExpect(jsonPath("$.[0].jobRole").value("Role"));
        resultActions.andExpect(jsonPath("$.[0].jobDescription").value("Description"));
        resultActions.andExpect(jsonPath("$.[0].createdAt").isNotEmpty());
        resultActions.andExpect(jsonPath("$.[0].updatedAt").isNotEmpty());
    }

    private void checkJobResponseFieldByField(ResultActions resultActions) throws Exception {
        resultActions.andExpect(jsonPath("$.startDate").value("2022-05-18"));
        resultActions.andExpect(jsonPath("$.endDate").value("2022-05-19"));
        resultActions.andExpect(jsonPath("$.companyName").value("Company"));
        resultActions.andExpect(jsonPath("$.jobRole").value("Role"));
        resultActions.andExpect(jsonPath("$.jobDescription").value("Description"));
        resultActions.andExpect(jsonPath("$.createdAt").isNotEmpty());
        resultActions.andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

}
