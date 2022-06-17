package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.*;
import com.bravi.portfolio.entity.Technology;
import com.bravi.portfolio.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bravi.portfolio.util.MapperUtil.*;

@AllArgsConstructor
@Component
public class TechnologyMapperImpl implements IMapper<Technology, TechnologyRequest, TechnologyResponse> {

    private final PersonaRepository personaRepository;

    @Override
    public Technology toEntity(TechnologyRequest request) {
        return Technology.builder()
                .technologyName(request.getTechnologyName())
                .technologyLevel(request.getTechnologyLevel())
                .persona(
                        mapNullableObjectWithFilter(
                                request.getPersonaId(),
                                personaRepository::existsById,
                                personaRepository::getReferenceById
                        )
                )
                .build();
    }

    @Override
    public Technology toEntity(Technology entity, TechnologyRequest request) {
        entity.setTechnologyName(request.getTechnologyName());
        entity.setTechnologyLevel(request.getTechnologyLevel());
        entity.setPersona(
                mapNullableObjectWithFilter(
                        request.getPersonaId(),
                        personaRepository::existsById,
                        personaRepository::getReferenceById
                )
        );
        return entity;
    }

    @Override
    public TechnologyResponse toDto(Technology entity) {
        return RelationshipMapper.mapTechnologyResponse(entity, null);
    }

    @Override
    public List<TechnologyResponse> toDtoList(List<Technology> list) {
        return streamNullableList(list, this::toDto);
    }
}
