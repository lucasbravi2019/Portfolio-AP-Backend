package com.bravi.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactResponse {

    private Long id;

    private String description;

    private String contact;

    private PersonaResponse persona;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
