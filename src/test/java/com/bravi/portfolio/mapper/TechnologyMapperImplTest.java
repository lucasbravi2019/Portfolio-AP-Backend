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
class TechnologyMapperImplTest {

    @Mock
    PersonaRepository personaRepository;

    TechnologyMapperImpl technologyMapper;
    Persona persona;
    Technology technology;

    @BeforeEach
    void setUp() {
        technologyMapper = new TechnologyMapperImpl(personaRepository);
        assertThat(technologyMapper).isNotNull();
        persona = BuilderUtil.buildPersona();
        technology = BuilderUtil.buildTechnology(persona);
    }

    @Test
    void toEntity() {
        //Given
        TechnologyRequest technologyRequest = BuilderUtil.buildTechnologyRequest(1L);

        //When
        when(personaRepository.existsById(1L)).thenReturn(true);
        when(personaRepository.getReferenceById(1L)).thenReturn(persona);

        //Then
        Technology technology = technologyMapper.toEntity(technologyRequest);

        assertThat(technology).isNotNull();

        compareTechnologyFieldByField(technology, technologyRequest);

        verify(personaRepository, atLeastOnce()).existsById(1L);
        verify(personaRepository, atLeastOnce()).getReferenceById(1L);
    }

    @Test
    void testToEntity() {
        //Given
        TechnologyRequest technologyRequest = BuilderUtil.buildTechnologyRequest(1L);
        Technology entity = Technology.builder().id(1L).build();

        //When
        when(personaRepository.existsById(1L)).thenReturn(true);
        when(personaRepository.getReferenceById(1L)).thenReturn(persona);

        //Then
        Technology technology = technologyMapper.toEntity(entity, technologyRequest);

        assertThat(technology).isNotNull();
        assertThat(technology.getId()).isEqualTo(1L);

        compareTechnologyFieldByField(technology, technologyRequest);

        verify(personaRepository, atLeastOnce()).existsById(1L);
        verify(personaRepository, atLeastOnce()).getReferenceById(1L);
    }

    @Test
    void toDto() {
        //Given
        Technology technologyEntity = this.technology;

        //When
        TechnologyResponse technologyResponse = technologyMapper.toDto(technologyEntity);

        //Then
        assertThat(technologyResponse).isNotNull();

        compareTechnologyResponseFieldByField(technologyResponse);
    }

    @Test
    void toDtoList() {
        //Given
        List<Technology> technologyList = List.of(this.technology);

        //When
        List<TechnologyResponse> technologyResponses = technologyMapper.toDtoList(technologyList);

        //Then
        assertThat(technologyResponses).isNotNull();
        assertThat(technologyResponses).isNotEmpty();
        assertThat(technologyResponses.get(0).getPersona()).isNotNull();

        compareTechnologyResponseFieldByField(technologyResponses.get(0));
    }

    private void compareTechnologyFieldByField(Technology technology, TechnologyRequest request) {
        assertThat(technology.getTechnologyName()).isEqualTo(request.getTechnologyName());
        assertThat(technology.getTechnologyLevel()).isEqualTo(request.getTechnologyLevel());
        assertThat(technology.getPersona()).isNotNull();
        comparePersonaFieldByField(technology.getPersona());
    }

    private void compareTechnologyResponseFieldByField(TechnologyResponse technology) {
        assertThat(technology.getId()).isEqualTo(this.technology.getId());
        assertThat(technology.getTechnologyName()).isEqualTo(this.technology.getTechnologyName());
        assertThat(technology.getTechnologyLevel()).isEqualTo(this.technology.getTechnologyLevel());
        assertThat(technology.getPersona()).isNotNull();
        comparePersonaFieldByField(technology.getPersona());
    }

    private void comparePersonaFieldByField(Persona persona) {
        assertThat(persona.getId()).isEqualTo(this.persona.getId());
        assertThat(persona.getFirstName()).isEqualTo(this.persona.getFirstName());
        assertThat(persona.getLastName()).isEqualTo(this.persona.getLastName());
        assertThat(persona.getContactList()).isEqualTo(this.persona.getContactList());
        assertThat(persona.getJobList()).isEqualTo(this.persona.getJobList());
        assertThat(persona.getTechnologyList()).isEqualTo(this.persona.getTechnologyList());
    }

    private void comparePersonaFieldByField(PersonaResponse persona) {
        assertThat(persona.getId()).isEqualTo(this.persona.getId());
        assertThat(persona.getFirstName()).isEqualTo(this.persona.getFirstName());
        assertThat(persona.getLastName()).isEqualTo(this.persona.getLastName());
        compareContactList(persona.getContactList().get(0), this.persona.getContactList().get(0));
        compareJobList(persona.getJobList().get(0), this.persona.getJobList().get(0));
        assertThat(persona.getTechnologyList()).isEmpty();
    }

    private void compareContactList(ContactResponse contactResponse, Contact contact) {
        assertThat(contactResponse.getId()).isEqualTo(contact.getId());
        assertThat(contactResponse.getDescription()).isEqualTo(contact.getDescription());
        assertThat(contactResponse.getContact()).isEqualTo(contact.getContact());
        assertThat(contactResponse.getPersona()).isEqualTo(contact.getPersona());
        assertThat(contactResponse.getCreatedAt()).isEqualTo(contact.getCreatedAt());
        assertThat(contactResponse.getUpdatedAt()).isEqualTo(contact.getUpdatedAt());
        assertThat(contactResponse.getPersona()).isNull();
    }

    private void compareJobList(JobResponse jobResponse, Job job) {
        assertThat(jobResponse.getId()).isEqualTo(job.getId());
        assertThat(jobResponse.getStartDate()).isEqualTo(job.getStartDate());
        assertThat(jobResponse.getEndDate()).isEqualTo(job.getEndDate());
        assertThat(jobResponse.getCompanyName()).isEqualTo(job.getCompanyName());
        assertThat(jobResponse.getJobDescription()).isEqualTo(job.getJobDescription());
        assertThat(jobResponse.getJobRole()).isEqualTo(job.getJobRole());
        assertThat(jobResponse.getCreatedAt()).isEqualTo(job.getCreatedAt());
        assertThat(jobResponse.getUpdatedAt()).isEqualTo(job.getUpdatedAt());
        assertThat(jobResponse.getPersona()).isNull();
    }

}