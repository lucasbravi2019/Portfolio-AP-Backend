package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.*;
import com.bravi.portfolio.entity.Persona;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bravi.portfolio.util.MapperUtil.streamNullableList;

@Component
public class PersonaMapperImpl implements IMapper<Persona, PersonaRequest, PersonaResponse> {

    @Override
    public Persona toEntity(PersonaRequest request) {
        return Persona.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

    }

    @Override
    public Persona toEntity(Persona entity, PersonaRequest request) {
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        return entity;
    }

    @Override
    public PersonaResponse toDto(Persona entity) {
        return RelationshipMapper.mapPersonaResponse(entity);
    }

    @Override
    public List<PersonaResponse> toDtoList(List<Persona> list) {
        return streamNullableList(list, this::toDto);
    }
}
