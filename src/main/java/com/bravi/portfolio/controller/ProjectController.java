package com.bravi.portfolio.controller;

import com.bravi.portfolio.dto.ProjectRequest;
import com.bravi.portfolio.dto.ProjectResponse;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.service.impl.ProjectServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(PathName.PROJECT)
public class ProjectController {

    private final ProjectServiceImpl projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(projectRequest));
    }

    @GetMapping(PathName.PATH_ID)
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @PutMapping(PathName.PATH_ID)
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectRequest) {
        return ResponseEntity.ok(projectService.updateProject(id, projectRequest));
    }

    @DeleteMapping(PathName.PATH_ID)
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

}
