package com.bravi.portfolio.service;

import com.bravi.portfolio.dto.ProjectRequest;
import com.bravi.portfolio.dto.ProjectResponse;

import java.util.List;

public interface IProjectService {

    List<ProjectResponse> getAllProjects();
    ProjectResponse getProject(Long id);
    ProjectResponse createProject(ProjectRequest projectRequest);
    ProjectResponse updateProject(Long id, ProjectRequest projectRequest);
    void deleteProject(Long id);

}
