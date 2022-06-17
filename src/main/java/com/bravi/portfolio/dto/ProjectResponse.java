package com.bravi.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectResponse {

    private Long id;

    private String projectName;

    private String description;

    private String site;

    private String image;

    private PersonaResponse persona;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
