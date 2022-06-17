package com.bravi.portfolio.service.impl;

import com.bravi.portfolio.dto.PersonaRequest;
import com.bravi.portfolio.dto.PersonaResponse;
import com.bravi.portfolio.entity.Persona;
import com.bravi.portfolio.mapper.PersonaMapperImpl;
import com.bravi.portfolio.repository.PersonaRepository;
import com.bravi.portfolio.util.BuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonaServiceImplTest {

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private PersonaMapperImpl personaMapper;

    @InjectMocks
    private PersonaServiceImpl personaService;

    Persona persona;

    @BeforeEach
    void setUp() {
        persona = BuilderUtil.buildPersona();
    }

    @Test
    void getPersona() {
        when(personaRepository.findFirstByOrderById()).thenReturn(Optional.ofNullable(persona));
        when(personaMapper.toDto(persona)).thenReturn(new PersonaResponse());

        personaService.getPersona();

        verify(personaRepository, atLeastOnce()).findFirstByOrderById();
        verify(personaMapper, atLeastOnce()).toDto(persona);
    }

    @Test
    void createPersona() {
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);
        when(personaMapper.toEntity(any(PersonaRequest.class))).thenReturn(persona);
        when(personaMapper.toDto(persona)).thenReturn(new PersonaResponse());

        PersonaRequest personaRequest = new PersonaRequest();
        personaService.createPersona(personaRequest);

        verify(personaRepository, atLeastOnce()).save(persona);
        verify(personaMapper, atLeastOnce()).toDto(persona);
        verify(personaMapper, atLeastOnce()).toEntity(personaRequest);
    }

    @Test
    void updatePersona() {
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);
        when(personaRepository.findById(1L)).thenReturn(Optional.ofNullable(persona));
        when(personaMapper.toEntity(any(Persona.class), any(PersonaRequest.class))).thenReturn(persona);
        when(personaMapper.toDto(any(Persona.class))).thenReturn(new PersonaResponse());

        PersonaRequest personaRequest = new PersonaRequest();
        personaService.updatePersona(1L, personaRequest);

        verify(personaRepository, atLeastOnce()).save(persona);
        verify(personaMapper, atLeastOnce()).toEntity(persona, personaRequest);
        verify(personaMapper, atLeastOnce()).toDto(persona);
    }
}