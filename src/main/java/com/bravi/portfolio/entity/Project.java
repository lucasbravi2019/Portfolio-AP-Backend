package com.bravi.portfolio.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@SuperBuilder
@Data
@Entity
@NoArgsConstructor
public class Project extends AbstractEntity {

    private String projectName;

    private String description;

    private String site;

    private String image;

    @ManyToOne
    private Persona persona;

}
