package com.bravi.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonaResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private List<TechnologyResponse> technologyList;

    private List<ContactResponse> contactList;

    private List<JobResponse> jobList;

    private AboutResponse about;

    private List<ProjectResponse> projectList;

    private List<EducationResponse> educationList;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
