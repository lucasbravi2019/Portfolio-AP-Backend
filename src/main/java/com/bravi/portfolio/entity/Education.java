package com.bravi.portfolio.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@SuperBuilder
@Data
@Entity
@NoArgsConstructor
public class Education extends AbstractEntity {

    private String institute;

    private String title;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate endDate;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    private Persona persona;

}
