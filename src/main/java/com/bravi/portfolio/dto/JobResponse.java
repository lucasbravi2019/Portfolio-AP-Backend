package com.bravi.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobResponse {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate endDate;

    private String companyName;

    private String jobRole;

    private String jobDescription;

    private PersonaResponse persona;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
