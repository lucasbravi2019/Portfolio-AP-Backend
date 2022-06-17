package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.*;
import com.bravi.portfolio.entity.Contact;
import com.bravi.portfolio.entity.Job;
import com.bravi.portfolio.entity.Persona;
import com.bravi.portfolio.entity.Technology;
import com.bravi.portfolio.util.BuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PersonaMapperImplTest {

    PersonaMapperImpl personaMapper;

    Persona persona = BuilderUtil.buildPersona();
    PersonaRequest personaRequest = BuilderUtil.buildPersonaRequest();

    PersonaResponse personaResponse = BuilderUtil.buildPersonaResponse();

    @BeforeEach
    void setUp() {
        personaMapper = new PersonaMapperImpl();
    }

    @Test
    void toEntity() {
        //Given
        PersonaRequest personaRequest = this.personaRequest;

        //When
        Persona persona = personaMapper.toEntity(personaRequest);

        //Then
        assertThat(persona).isNotNull();
        assertThat(persona).isInstanceOf(Persona.class);
        compareFieldByField(personaRequest, persona);
    }

    @Test
    void testToEntity() {
        //Given
        PersonaRequest personaRequest = this.personaRequest;
        Persona entity = Persona.builder().id(1L).build();

        //When
        Persona persona = personaMapper.toEntity(entity, personaRequest);

        //Then
        assertThat(persona).isNotNull();
        assertThat(persona).isInstanceOf(Persona.class);
        assertThat(persona.getId()).isEqualTo(1L);
        compareFieldByField(personaRequest, persona);
    }

    @Test
    void toDto() {
        //Given
        Persona persona = this.persona;

        //When
        PersonaResponse personaResponse = personaMapper.toDto(persona);

        //Then
        assertThat(personaResponse).isNotNull();
        assertThat(personaResponse).isInstanceOf(PersonaResponse.class);
        compareFieldByField(personaResponse, persona);
    }

    @Test
    void toDtoList() {
        //Given
        List<Persona> personaList = List.of(this.persona);

        //When
        List<PersonaResponse> personaResponses = personaMapper.toDtoList(personaList);

        //Then
        assertThat(personaResponses).isNotNull();
        assertThat(personaResponses).isNotEmpty();

        PersonaResponse personaResponse1 = personaResponses.get(0);

        compareFieldByField(personaResponse1, this.persona);
    }

    private void compareFieldByField(PersonaRequest personaRequest, Persona persona) {
        assertThat(personaRequest.getFirstName()).isEqualTo(persona.getFirstName());
        assertThat(personaRequest.getLastName()).isEqualTo(persona.getLastName());
    }

    private void compareFieldByField(PersonaResponse personaResponse, Persona persona) {
        assertThat(personaResponse.getId()).isEqualTo(persona.getId());
        assertThat(personaResponse.getFirstName()).isEqualTo(persona.getFirstName());
        assertThat(personaResponse.getLastName()).isEqualTo(persona.getLastName());
        compareJobResponseFieldByField(personaResponse, persona);
        compareTechnologyResponseFieldByField(personaResponse, persona);
        compareContactResponseFieldByField(personaResponse, persona);
    }

    private void compareJobResponseFieldByField(PersonaResponse personaResponse, Persona persona) {
        JobResponse jobResponse = personaResponse.getJobList().get(0);
        Job job = persona.getJobList().get(0);
        assertThat(jobResponse.getId()).isEqualTo(job.getId());
        assertThat(jobResponse.getJobDescription()).isEqualTo(job.getJobDescription());
        assertThat(jobResponse.getJobRole()).isEqualTo(job.getJobRole());
        assertThat(jobResponse.getStartDate()).isEqualTo(job.getStartDate());
        assertThat(jobResponse.getEndDate()).isEqualTo(job.getEndDate());
        assertThat(jobResponse.getCreatedAt()).isEqualTo(job.getCreatedAt());
        assertThat(jobResponse.getUpdatedAt()).isEqualTo(job.getUpdatedAt());
        assertThat(jobResponse.getCompanyName()).isEqualTo(job.getCompanyName());
        assertThat(jobResponse.getPersona()).isNull();
    }

    private void compareTechnologyResponseFieldByField(PersonaResponse personaResponse, Persona persona) {
        TechnologyResponse technologyResponse = personaResponse.getTechnologyList().get(0);
        Technology technology = persona.getTechnologyList().get(0);
        assertThat(technologyResponse.getId()).isEqualTo(technology.getId());
        assertThat(technologyResponse.getTechnologyName()).isEqualTo(technology.getTechnologyName());
        assertThat(technologyResponse.getTechnologyLevel()).isEqualTo(technology.getTechnologyLevel());
        assertThat(technologyResponse.getCreatedAt()).isEqualTo(technology.getCreatedAt());
        assertThat(technologyResponse.getUpdatedAt()).isEqualTo(technology.getUpdatedAt());
        assertThat(technologyResponse.getPersona()).isNull();
    }

    private void compareContactResponseFieldByField(PersonaResponse personaResponse, Persona persona) {
        ContactResponse contactResponse = personaResponse.getContactList().get(0);
        Contact contact = persona.getContactList().get(0);
        assertThat(contactResponse.getId()).isEqualTo(contact.getId());
        assertThat(contactResponse.getDescription()).isEqualTo(contact.getDescription());
        assertThat(contactResponse.getContact()).isEqualTo(contact.getContact());
        assertThat(contactResponse.getCreatedAt()).isEqualTo(contact.getCreatedAt());
        assertThat(contactResponse.getUpdatedAt()).isEqualTo(contact.getUpdatedAt());
        assertThat(contactResponse.getPersona()).isNull();
    }
}