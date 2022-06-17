package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.EducationRequest;
import com.bravi.portfolio.dto.EducationResponse;
import com.bravi.portfolio.entity.Education;
import com.bravi.portfolio.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bravi.portfolio.util.MapperUtil.mapNullableObjectWithFilter;
import static com.bravi.portfolio.util.MapperUtil.streamNullableList;

@AllArgsConstructor
@Component
public class EducationMapperImpl implements IMapper<Education, EducationRequest, EducationResponse> {

    private final PersonaRepository personaRepository;

    @Override
    public Education toEntity(EducationRequest request) {
        return Education.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .institute(request.getInstitute())
                .title(request.getTitle())
                .persona(
                        mapNullableObjectWithFilter(
                                request.getPersonaId(),
                                personaRepository::existsById,
                                personaRepository::getReferenceById
                        )
                ).build();
    }

    @Override
    public Education toEntity(Education entity, EducationRequest request) {
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setInstitute(request.getInstitute());
        entity.setTitle(request.getTitle());
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
    public EducationResponse toDto(Education entity) {
        return RelationshipMapper.mapEducationResponse(entity, null);
    }

    @Override
    public List<EducationResponse> toDtoList(List<Education> list) {
        return streamNullableList(list, this::toDto);
    }
}
