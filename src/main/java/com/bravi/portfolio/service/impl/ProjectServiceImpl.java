package com.bravi.portfolio.service.impl;

import com.bravi.portfolio.dto.ProjectRequest;
import com.bravi.portfolio.dto.ProjectResponse;
import com.bravi.portfolio.entity.Project;
import com.bravi.portfolio.mapper.ProjectMapperImpl;
import com.bravi.portfolio.repository.ProjectRepository;
import com.bravi.portfolio.service.IProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProjectServiceImpl implements IProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapperImpl projectMapper;

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectMapper.toDtoList(projectRepository.findAll());
    }

    @Override
    public ProjectResponse getProject(Long id) {
        return projectMapper.toDto(projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found.")));
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(projectRequest)));
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        Project entity = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found."));
        return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(entity, projectRequest)));
    }

    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) throw new RuntimeException("Project not found.");
        projectRepository.deleteById(id);
    }
}
