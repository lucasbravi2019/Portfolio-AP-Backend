package com.bravi.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobRequest {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate endDate;

    @NotEmpty
    private String companyName;

    @NotEmpty
    private String jobRole;

    @NotEmpty
    private String jobDescription;

    private Long personaId;

}
