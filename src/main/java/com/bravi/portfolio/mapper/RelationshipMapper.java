package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.*;
import com.bravi.portfolio.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

import static com.bravi.portfolio.util.MapperUtil.*;

@Slf4j
public class RelationshipMapper {

    public static <T> JobResponse mapJobResponse(Job job, Class<T> excludeClass) {
        log.info("equals: {} == {}: {}", excludeClass, Job.class, ObjectUtils.nullSafeEquals(excludeClass, Job.class));
        return JobResponse.builder()
                .id(job.getId())
                .jobDescription(job.getJobDescription())
                .jobRole(job.getJobRole())
                .companyName(job.getCompanyName())
                .persona(
                        ObjectUtils.nullSafeEquals(excludeClass, Job.class) ? null :
                        mapNullableObject(
                                job.getPersona(),
                                RelationshipMapper::mapPersonaResponse
                        )
                ).startDate(job.getStartDate())
                .endDate(job.getEndDate())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }

    public static <T> TechnologyResponse mapTechnologyResponse(Technology technology, Class<T> excludeClass) {
        log.info("equals: {} == {}: {}", excludeClass, Technology.class, ObjectUtils.nullSafeEquals(excludeClass, Technology.class));
        return TechnologyResponse.builder()
                .id(technology.getId())
                .technologyName(technology.getTechnologyName())
                .technologyLevel(technology.getTechnologyLevel())
                .persona(
                        ObjectUtils.nullSafeEquals(excludeClass, Technology.class) ? null :
                        mapNullableObject(
                                technology.getPersona(),
                                RelationshipMapper::mapPersonaResponse
                        )
                ).createdAt(technology.getCreatedAt())
                .updatedAt(technology.getUpdatedAt())
                .build();
    }

    public static <T> ContactResponse mapContactResponse(Contact contact, Class<T> excludeClass) {
        log.info("equals: {} == {}: {}", excludeClass, Contact.class, ObjectUtils.nullSafeEquals(excludeClass, Contact.class));
        return ContactResponse.builder()
                .id(contact.getId())
                .description(contact.getDescription())
                .contact(contact.getContact())
                .persona(
                        ObjectUtils.nullSafeEquals(excludeClass, Contact.class) ? null :
                        mapNullableObject(
                                contact.getPersona(),
                                RelationshipMapper::mapPersonaResponse
                        )
                ).createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .build();
    }

    public static <T> AboutResponse mapAboutResponse(About about, Class<?> excludeClass) {
        log.info("equals: {} == {}: {}", excludeClass, About.class, ObjectUtils.nullSafeEquals(excludeClass, About.class));
        return AboutResponse.builder()
                .id(about.getId())
                .title(about.getTitle())
                .image(about.getImage())
                .about(about.getAbout())
                .createdAt(about.getCreatedAt())
                .updatedAt(about.getUpdatedAt())
                .persona(
                        ObjectUtils.nullSafeEquals(excludeClass, About.class) ? null :
                        mapNullableObject(
                                about.getPersona(),
                                RelationshipMapper::mapPersonaResponse
                        )
                ).build();
    }

    public static <T> EducationResponse mapEducationResponse(Education education, Class<T> excludeClass) {
        log.info("equals: {} == {}: {}", excludeClass, Education.class, ObjectUtils.nullSafeEquals(excludeClass, Education.class));
        return EducationResponse.builder()
                .id(education.getId())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .institute(education.getInstitute())
                .title(education.getTitle())
                .persona(
                        ObjectUtils.nullSafeEquals(excludeClass, Education.class) ? null :
                        mapNullableObject(
                                education.getPersona(),
                                RelationshipMapper::mapPersonaResponse
                        )
                ).build();
    }

    public static <T> ProjectResponse mapProjectResponse(Project project, Class<T> excludeClass) {
        log.info("equals: {} == {}: {}", excludeClass, Project.class, ObjectUtils.nullSafeEquals(excludeClass, Project.class));
        return ProjectResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .image(project.getImage())
                .site(project.getSite())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .persona(
                        ObjectUtils.nullSafeEquals(excludeClass, Project.class) ? null :
                        mapNullableObject(
                                project.getPersona(),
                                RelationshipMapper::mapPersonaResponse
                        )
                ).build();
    }

    public static PersonaResponse mapPersonaResponse(Persona persona) {
        return PersonaResponse.builder()
                .id(persona.getId())
                .firstName(persona.getFirstName())
                .lastName(persona.getLastName())
                .createdAt(persona.getCreatedAt())
                .updatedAt(persona.getUpdatedAt())
                .about(
                        mapNullableObject(persona.getAbout(), about -> mapAboutResponse(about, About.class))
                ).technologyList(
                        streamNullableList(
                                persona.getTechnologyList(),
                                response -> mapTechnologyResponse(response, Technology.class)
                        )
                ).contactList(
                        streamNullableList(
                                persona.getContactList(),
                                response -> mapContactResponse(response, Contact.class)
                        )
                ).jobList(
                        streamNullableList(
                                persona.getJobList(),
                                response -> mapJobResponse(response, Job.class)
                        )
                ).educationList(
                        streamNullableList(
                                persona.getEducationList(),
                                response -> mapEducationResponse(response, Education.class)
                        )
                ).projectList(
                        streamNullableList(
                                persona.getProjectList(),
                                response -> mapProjectResponse(response, Project.class)
                        )
                ).build();
    }

}
