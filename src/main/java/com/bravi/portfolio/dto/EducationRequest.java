package com.bravi.portfolio.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Builder
public class EducationRequest {

    @NotBlank
    private String institute;

    @NotBlank
    private String title;

    @NotNull
    @PastOrPresent
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Long personaId;

}
