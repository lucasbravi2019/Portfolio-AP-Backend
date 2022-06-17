package com.bravi.portfolio.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class Job extends AbstractEntity {

    @NotNull
    @PastOrPresent
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotBlank
    private String companyName;

    @NotBlank
    private String jobRole;

    @NotBlank
    private String jobDescription;

    @ManyToOne
    private Persona persona;

}
