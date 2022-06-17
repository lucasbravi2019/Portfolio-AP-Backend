package com.bravi.portfolio.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class AboutRequest {

    @NotBlank
    private String image;

    @NotBlank
    private String title;

    @NotBlank
    private String about;

    private Long personaId;

}
