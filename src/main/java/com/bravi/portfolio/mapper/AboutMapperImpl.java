package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.*;
import com.bravi.portfolio.entity.*;
import com.bravi.portfolio.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bravi.portfolio.util.MapperUtil.*;

@AllArgsConstructor
@Component
public class AboutMapperImpl implements IMapper<About, AboutRequest, AboutResponse> {

    private final PersonaRepository personaRepository;

    @Override
    public About toEntity(AboutRequest request) {
        return About.builder()
                .title(request.getTitle())
                .about(request.getAbout())
                .image(request.getImage())
                .persona(
                        mapNullableObjectWithFilter(
                                request.getPersonaId(),
                                personaRepository::existsById,
                                personaRepository::getReferenceById)
                ).build();
    }

    @Override
    public About toEntity(About entity, AboutRequest request) {
        entity.setTitle(request.getTitle());
        entity.setAbout(request.getAbout());
        entity.setImage(request.getImage());
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
    public AboutResponse toDto(About entity) {
        return RelationshipMapper.mapAboutResponse(entity, null);
    }

    @Override
    public List<AboutResponse> toDtoList(List<About> list) {
        return null;
    }




}
