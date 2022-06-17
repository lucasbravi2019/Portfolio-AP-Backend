package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.PersonaResponse;
import com.bravi.portfolio.dto.ProjectRequest;
import com.bravi.portfolio.dto.ProjectResponse;
import com.bravi.portfolio.entity.Project;
import com.bravi.portfolio.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bravi.portfolio.util.MapperUtil.*;

@Component
@AllArgsConstructor
public class ProjectMapperImpl implements IMapper<Project, ProjectRequest, ProjectResponse> {

    private final PersonaRepository personaRepository;

    @Override
    public Project toEntity(ProjectRequest request) {
        return Project.builder()
                .projectName(request.getProjectName())
                .description(request.getDescription())
                .site(request.getSite())
                .image(request.getImage())
                .persona(
                        mapNullableObjectWithFilter(
                                request.getPersonaId(),
                                personaRepository::existsById,
                                personaRepository::getReferenceById
                        )
                ).build();
    }

    @Override
    public Project toEntity(Project entity, ProjectRequest request) {
        entity.setProjectName(request.getProjectName());
        entity.setDescription(request.getDescription());
        entity.setImage(request.getImage());
        entity.setSite(request.getSite());
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
    public ProjectResponse toDto(Project entity) {
        return RelationshipMapper.mapProjectResponse(entity, null);
    }

    @Override
    public List<ProjectResponse> toDtoList(List<Project> list) {
        return streamNullableList(list, this::toDto);
    }
}
