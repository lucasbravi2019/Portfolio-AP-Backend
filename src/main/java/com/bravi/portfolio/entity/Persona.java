package com.bravi.portfolio.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Persona extends AbstractEntity {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @OneToOne(mappedBy = "persona", orphanRemoval = true)
    private About about;

    @OneToMany(mappedBy = "persona", orphanRemoval = true)
    private List<Contact> contactList;

    @OneToMany(mappedBy = "persona", orphanRemoval = true)
    private List<Technology> technologyList;

    @OneToMany(mappedBy = "persona", orphanRemoval = true)
    private List<Job> jobList;

    @OneToMany(mappedBy = "persona", orphanRemoval = true)
    private List<Project> projectList;

    @OneToMany(mappedBy = "persona", orphanRemoval = true)
    private List<Education> educationList;

}
