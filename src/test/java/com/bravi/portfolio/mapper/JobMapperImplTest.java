package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.*;
import com.bravi.portfolio.entity.Contact;
import com.bravi.portfolio.entity.Job;
import com.bravi.portfolio.entity.Persona;
import com.bravi.portfolio.entity.Technology;
import com.bravi.portfolio.repository.PersonaRepository;
import com.bravi.portfolio.util.BuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobMapperImplTest {

    @Mock
    PersonaRepository personaRepository;

    JobMapperImpl jobMapper;
    Persona persona;

    @BeforeEach
    void setUp() {
        jobMapper = new JobMapperImpl(personaRepository);
        persona = BuilderUtil.buildPersona();
    }

    @Test
    void toEntity() {
        //Given
        JobRequest jobRequest = BuilderUtil.buildJobRequest(persona.getId());

        //When
        when(personaRepository.existsById(1L)).thenReturn(true);
        when(personaRepository.getReferenceById(1L)).thenReturn(persona);
        Job job = jobMapper.toEntity(jobRequest);

        //Then
        assertThat(job).isNotNull();

        verify(personaRepository, atLeastOnce()).existsById(1L);
        verify(personaRepository, atLeastOnce()).getReferenceById(1L);

        compareJobFieldByField(job, jobRequest);
    }

    @Test
    void testToEntity() {
        //Given
        JobRequest jobRequest = BuilderUtil.buildJobRequest(persona.getId());
        Job entity = Job.builder().id(1L).build();

        //When
        when(personaRepository.existsById(1L)).thenReturn(true);
        when(personaRepository.getReferenceById(1L)).thenReturn(persona);
        Job job = jobMapper.toEntity(entity, jobRequest);

        //Then
        assertThat(job).isNotNull();
        assertThat(job.getId()).isEqualTo(1L);

        verify(personaRepository, atLeastOnce()).existsById(1L);
        verify(personaRepository, atLeastOnce()).getReferenceById(1L);

        compareJobFieldByField(job, jobRequest);
    }

    @Test
    void toDto() {
        //Given
        Job job = BuilderUtil.buildJob(persona);

        //When
        JobResponse jobResponse = jobMapper.toDto(job);

        //Then
        assertThat(jobResponse).isNotNull();
        compareJobResponseFieldByField(jobResponse, job);
    }

    @Test
    void toDtoList() {
        //Given
        List<Job> jobList = List.of(BuilderUtil.buildJob(persona));

        //When
        List<JobResponse> jobResponse = jobMapper.toDtoList(jobList);

        //Then
        assertThat(jobResponse).isNotNull();
        assertThat(jobResponse).isNotEmpty();
        compareJobResponseFieldByField(jobResponse.get(0), jobList.get(0));
    }

    private void compareJobFieldByField(Job job, JobRequest jobRequest) {
        assertThat(job.getStartDate()).isEqualTo(jobRequest.getStartDate());
        assertThat(job.getEndDate()).isEqualTo(jobRequest.getEndDate());
        assertThat(job.getJobDescription()).isEqualTo(jobRequest.getJobDescription());
        assertThat(job.getJobRole()).isEqualTo(jobRequest.getJobRole());
        assertThat(job.getCompanyName()).isEqualTo(jobRequest.getCompanyName());
        assertThat(job.getPersona()).isNotNull();
        comparePersonaFieldByField(job.getPersona());
    }

    private void comparePersonaFieldByField(Persona jobPersona) {
        assertThat(jobPersona.getId()).isEqualTo(this.persona.getId());
        assertThat(jobPersona.getFirstName()).isEqualTo(this.persona.getFirstName());
        assertThat(jobPersona.getLastName()).isEqualTo(this.persona.getLastName());
        assertThat(jobPersona.getContactList()).isEqualTo(this.persona.getContactList());
        assertThat(jobPersona.getTechnologyList()).isEqualTo(this.persona.getTechnologyList());
        assertThat(jobPersona.getJobList()).isEqualTo(this.persona.getJobList());
    }

    private void compareJobResponseFieldByField(JobResponse jobResponse, Job job) {
        assertThat(jobResponse.getId()).isEqualTo(job.getId());
        assertThat(jobResponse.getStartDate()).isEqualTo(job.getStartDate());
        assertThat(jobResponse.getEndDate()).isEqualTo(job.getEndDate());
        assertThat(jobResponse.getJobDescription()).isEqualTo(job.getJobDescription());
        assertThat(jobResponse.getJobRole()).isEqualTo(job.getJobRole());
        assertThat(jobResponse.getCompanyName()).isEqualTo(job.getCompanyName());
        assertThat(jobResponse.getCreatedAt()).isEqualTo(job.getCreatedAt());
        assertThat(jobResponse.getUpdatedAt()).isEqualTo(job.getUpdatedAt());
        assertThat(jobResponse.getPersona()).isNotNull();
        comparePersonaResponseFieldByField(jobResponse.getPersona());
    }

    private void comparePersonaResponseFieldByField(PersonaResponse personaResponse) {
        assertThat(personaResponse.getId()).isEqualTo(this.persona.getId());
        assertThat(personaResponse.getFirstName()).isEqualTo(this.persona.getFirstName());
        assertThat(personaResponse.getLastName()).isEqualTo(this.persona.getLastName());
        compareTechnologyResponseFieldByField(personaResponse.getTechnologyList().get(0));
        compareContactResponseFieldByField(personaResponse.getContactList().get(0));
    }

    private void compareTechnologyResponseFieldByField(TechnologyResponse technologyResponse) {
        Technology technology = this.persona.getTechnologyList().get(0);
        assertThat(technologyResponse.getId()).isEqualTo(technology.getId());
        assertThat(technologyResponse.getTechnologyName()).isEqualTo(technology.getTechnologyName());
        assertThat(technologyResponse.getTechnologyLevel()).isEqualTo(technology.getTechnologyLevel());
        assertThat(technologyResponse.getCreatedAt()).isEqualTo(technology.getCreatedAt());
        assertThat(technologyResponse.getUpdatedAt()).isEqualTo(technology.getUpdatedAt());
        assertThat(technologyResponse.getPersona()).isNull();
    }

    private void compareContactResponseFieldByField(ContactResponse contactResponse) {
        Contact contact = this.persona.getContactList().get(0);
        assertThat(contactResponse.getId()).isEqualTo(contact.getId());
        assertThat(contactResponse.getDescription()).isEqualTo(contact.getDescription());
        assertThat(contactResponse.getContact()).isEqualTo(contact.getContact());
        assertThat(contactResponse.getCreatedAt()).isEqualTo(contact.getCreatedAt());
        assertThat(contactResponse.getUpdatedAt()).isEqualTo(contact.getUpdatedAt());
        assertThat(contactResponse.getPersona()).isNull();
    }
}