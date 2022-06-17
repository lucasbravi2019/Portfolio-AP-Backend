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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactMapperImplTest {

    @Mock
    PersonaRepository personaRepository;

    ContactMapperImpl contactMapper;

    Persona persona;

    @BeforeEach
    void setUp() {
        contactMapper = new ContactMapperImpl(personaRepository);
        persona = BuilderUtil.buildPersona();
    }

    @Test
    void toEntity() {
        //Given
        ContactRequest contactRequest = BuilderUtil.buildContactRequest(this.persona.getId());

        //When
        when(personaRepository.existsById(1L)).thenReturn(true);
        when(personaRepository.getReferenceById(1L)).thenReturn(this.persona);
        Contact contact = contactMapper.toEntity(contactRequest);

        //Then
        assertThat(contact).isNotNull();
        compareContactFieldByField(contact, contactRequest);
    }

    @Test
    void testToEntity() {
        //Given
        ContactRequest contactRequest = BuilderUtil.buildContactRequest(this.persona.getId());
        Contact entity = Contact.builder().id(1L).build();
        //When
        when(personaRepository.existsById(1L)).thenReturn(true);
        when(personaRepository.getReferenceById(1L)).thenReturn(this.persona);
        Contact contact = contactMapper.toEntity(entity, contactRequest);

        //Then
        assertThat(contact).isNotNull();
        assertThat(contact.getId()).isEqualTo(1L);

        compareContactFieldByField(contact, contactRequest);
    }

    @Test
    void toDto() {
        //Given
        Contact contact = BuilderUtil.buildContact(this.persona);

        //When
        ContactResponse contactResponse = contactMapper.toDto(contact);

        //Then
        assertThat(contactResponse).isNotNull();
        compareContactResponseFieldByField(contactResponse, contact);
    }

    @Test
    void toDtoList() {
        //Given
        List<Contact> contact = List.of(BuilderUtil.buildContact(this.persona));

        //When
        List<ContactResponse> contactResponse = contactMapper.toDtoList(contact);

        //Then
        assertThat(contactResponse).isNotNull();
        compareContactResponseFieldByField(contactResponse.get(0), contact.get(0));
    }

    private void compareContactFieldByField(Contact contact, ContactRequest contactRequest) {
        assertThat(contact.getDescription()).isEqualTo(contactRequest.getDescription());
        assertThat(contact.getContact()).isEqualTo(contactRequest.getContact());
        assertThat(contact.getPersona()).isNotNull();
        comparePersonaFieldByField(contact.getPersona());
    }

    private void comparePersonaFieldByField(Persona persona) {
        assertThat(persona.getId()).isEqualTo(this.persona.getId());
        assertThat(persona.getFirstName()).isEqualTo(this.persona.getFirstName());
        assertThat(persona.getLastName()).isEqualTo(this.persona.getLastName());
        assertThat(persona.getContactList()).isEqualTo(this.persona.getContactList());
        assertThat(persona.getJobList()).isEqualTo(this.persona.getJobList());
        assertThat(persona.getTechnologyList()).isEqualTo(this.persona.getTechnologyList());
    }

    private void compareContactResponseFieldByField(ContactResponse contactResponse, Contact contact) {
        assertThat(contactResponse.getId()).isEqualTo(contact.getId());
        assertThat(contactResponse.getDescription()).isEqualTo(contact.getDescription());
        assertThat(contactResponse.getContact()).isEqualTo(contact.getContact());
        assertThat(contactResponse.getCreatedAt()).isEqualTo(contact.getCreatedAt());
        assertThat(contactResponse.getUpdatedAt()).isEqualTo(contact.getUpdatedAt());
        assertThat(contactResponse.getPersona()).isNotNull();
        comparePersonaResponseFieldByField(contactResponse.getPersona());
    }

    private void comparePersonaResponseFieldByField(PersonaResponse personaResponse) {
        assertThat(personaResponse.getId()).isEqualTo(persona.getId());
        assertThat(personaResponse.getFirstName()).isEqualTo(persona.getFirstName());
        assertThat(personaResponse.getLastName()).isEqualTo(persona.getLastName());
        assertThat(personaResponse.getContactList()).isEmpty();
        assertThat(personaResponse.getTechnologyList()).isNotNull();
        assertThat(personaResponse.getJobList()).isNotNull();
        compareTechnologyResponseFieldByField(personaResponse.getTechnologyList().get(0));
        compareJobResponseFieldByField(personaResponse.getJobList().get(0));
    }

    private void compareTechnologyResponseFieldByField(TechnologyResponse technologyResponse) {
        Technology technology = persona.getTechnologyList().get(0);
        assertThat(technologyResponse.getId()).isEqualTo(technology.getId());
        assertThat(technologyResponse.getTechnologyName()).isEqualTo(technology.getTechnologyName());
        assertThat(technologyResponse.getTechnologyLevel()).isEqualTo(technology.getTechnologyLevel());
        assertThat(technologyResponse.getCreatedAt()).isEqualTo(technology.getCreatedAt());
        assertThat(technologyResponse.getUpdatedAt()).isEqualTo(technology.getUpdatedAt());
        assertThat(technologyResponse.getPersona()).isNull();
    }

    private void compareJobResponseFieldByField(JobResponse jobResponse) {
        Job job = persona.getJobList().get(0);
        assertThat(jobResponse.getId()).isEqualTo(job.getId());
        assertThat(jobResponse.getCompanyName()).isEqualTo(job.getCompanyName());
        assertThat(jobResponse.getJobDescription()).isEqualTo(job.getJobDescription());
        assertThat(jobResponse.getJobRole()).isEqualTo(job.getJobRole());
        assertThat(jobResponse.getCreatedAt()).isEqualTo(job.getCreatedAt());
        assertThat(jobResponse.getUpdatedAt()).isEqualTo(job.getUpdatedAt());
        assertThat(jobResponse.getStartDate()).isEqualTo(job.getStartDate());
        assertThat(jobResponse.getEndDate()).isEqualTo(job.getEndDate());
        assertThat(jobResponse.getPersona()).isNull();
    }

}