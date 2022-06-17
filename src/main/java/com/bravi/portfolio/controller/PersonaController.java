package com.bravi.portfolio.controller;

import com.bravi.portfolio.dto.PersonaRequest;
import com.bravi.portfolio.dto.PersonaResponse;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.service.impl.PersonaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(PathName.PERSONA)
public class PersonaController {

    private final PersonaServiceImpl personaServiceImpl;

    @GetMapping
    public ResponseEntity<PersonaResponse> getPersona() {
        return ResponseEntity.ok(personaServiceImpl.getPersona());
    }

    @PostMapping
    public ResponseEntity<PersonaResponse> createPersona(@Valid @RequestBody PersonaRequest persona) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personaServiceImpl.createPersona(persona));
    }

    @PutMapping(PathName.PATH_ID)
    public ResponseEntity<PersonaResponse> updatePersona(@PathVariable Long id, @Valid @RequestBody PersonaRequest persona) {
        return ResponseEntity.ok(personaServiceImpl.updatePersona(id, persona));
    }

}
