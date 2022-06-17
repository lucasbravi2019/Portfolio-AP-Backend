package com.bravi.portfolio.service;

import com.bravi.portfolio.dto.PersonaRequest;
import com.bravi.portfolio.dto.PersonaResponse;

public interface IPersonaService {

    PersonaResponse getPersona();

    PersonaResponse createPersona(PersonaRequest entity);

    PersonaResponse updatePersona(Long id, PersonaRequest entity);

}

