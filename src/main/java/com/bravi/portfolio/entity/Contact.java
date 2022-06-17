package com.bravi.portfolio.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Contact extends AbstractEntity {

    @NotBlank
    private String description;

    @NotBlank
    private String contact;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Persona persona;

}
