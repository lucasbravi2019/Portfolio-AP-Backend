package com.bravi.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AboutResponse {

    private Long id;

    private String image;

    private String title;

    private String about;

    private PersonaResponse persona;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
