package com.bravi.portfolio.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ProjectRequest {

    @NotBlank
    private String projectName;

    @NotBlank
    private String description;

    @NotBlank
    private String site;

    @NotBlank
    private String image;

    private Long personaId;

}
