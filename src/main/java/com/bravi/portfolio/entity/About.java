package com.bravi.portfolio.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@SuperBuilder
@Data
@Entity
@NoArgsConstructor
public class About extends AbstractEntity {

    private String image;

    private String title;

    private String about;

    @OneToOne
    private Persona persona;

}
