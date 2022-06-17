package com.bravi.portfolio.util;

import com.bravi.portfolio.dto.*;
import com.bravi.portfolio.entity.Contact;
import com.bravi.portfolio.entity.Job;
import com.bravi.portfolio.entity.Persona;
import com.bravi.portfolio.entity.Technology;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class BuilderUtil {

    public static Persona buildPersona() {
        return Persona.builder()
                .id(1L)
                .firstName("Lucas")
                .lastName("Bravi")
                .id(1L)
                .jobList(List.of(buildJob(null)))
                .contactList(List.of(buildContact(null)))
                .technologyList(List.of(buildTechnology(null)))
                .build();
    }

    public static Job buildJob(Persona persona) {
        if (persona != null) persona.setJobList(null);
        return Job.builder()
                .id(1L)
                .startDate(LocalDate.of(2022, 5, 18))
                .endDate(LocalDate.of(2022, 5, 19))
                .jobDescription("Job description")
                .companyName("Company name")
                .jobRole("Job role")
                .persona(persona)
                .build();
    }

    public static Contact buildContact(Persona persona) {
        if (persona != null) persona.setContactList(null);
        return Contact.builder()
                .id(1L)
                .description("Description")
                .contact("Contact")
                .persona(persona)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
    }

    public static Technology buildTechnology(Persona persona) {
        if (persona != null) persona.setTechnologyList(null);
        return Technology.builder()
                .id(1L)
                .technologyName("Java")
                .technologyLevel(10)
                .persona(persona)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
    }

    public static TechnologyRequest buildTechnologyRequest(Long personaId) {
        return TechnologyRequest.builder()
                .personaId(personaId)
                .technologyName("Java")
                .technologyLevel(15)
                .build();
    }

    public static JobRequest buildJobRequest(Long personaId) {
        return JobRequest.builder()
                .personaId(personaId)
                .startDate(LocalDate.of(2022, 05, 18))
                .endDate(LocalDate.of(2022, 05, 19))
                .companyName("Company")
                .jobDescription("Description")
                .jobRole("Role")
                .build();
    }

    public static PersonaResponse buildPersonaResponse() {
        return PersonaResponse.builder()
                .firstName("Lucas")
                .lastName("Bravi")
                .id(1L)
                .jobList(List.of(buildJobResponse(null)))
                .contactList(List.of(buildContactResponse(null)))
                .technologyList(List.of(buildTechnologyResponse(null)))
                .build();
    }

    public static JobResponse buildJobResponse(PersonaResponse personaResponse) {
        if (personaResponse != null) personaResponse.setJobList(null);
        return JobResponse.builder()
                .id(1L)
                .startDate(LocalDate.of(2022, 5, 18))
                .endDate(LocalDate.of(2022, 5, 19))
                .jobDescription("Job description")
                .companyName("Company name")
                .jobRole("Job role")
                .persona(personaResponse)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
    }

    public static ContactRequest buildContactRequest(Long personaId) {
        return ContactRequest.builder()
                .personaId(personaId)
                .contact("Contact")
                .description("Description")
                .build();
    }

    public static ContactResponse buildContactResponse(PersonaResponse personaResponse) {
        if (personaResponse != null) personaResponse.setContactList(null);
        return ContactResponse.builder()
                .id(1L)
                .description("Description")
                .contact("Contact")
                .persona(personaResponse)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
    }

    public static TechnologyResponse buildTechnologyResponse(PersonaResponse personaResponse) {
        if (personaResponse != null) personaResponse.setTechnologyList(null);
        return TechnologyResponse.builder()
                .id(1L)
                .technologyName("Java")
                .technologyLevel(10)
                .persona(personaResponse)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
    }

    public static PersonaRequest buildPersonaRequest() {
        return PersonaRequest.builder()
                .firstName("Lucas")
                .lastName("Bravi")
                .build();
    }



}
