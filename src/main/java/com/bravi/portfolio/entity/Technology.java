package com.bravi.portfolio.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class Technology extends AbstractEntity {

    @NotBlank
    private String technologyName;

    @Min(0)
    @Max(100)
    @NotNull
    private Integer technologyLevel;

    @ManyToOne
    private Persona persona;

}
