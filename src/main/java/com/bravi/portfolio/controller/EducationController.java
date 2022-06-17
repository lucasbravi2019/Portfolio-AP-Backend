package com.bravi.portfolio.controller;

import com.bravi.portfolio.dto.EducationRequest;
import com.bravi.portfolio.dto.EducationResponse;
import com.bravi.portfolio.entity.Education;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.service.impl.EducationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(PathName.EDUCATION)
public class EducationController {

    private final EducationServiceImpl educationService;

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getAllEducation() {
        return ResponseEntity.ok(educationService.getAllEducation());
    }

    @PostMapping
    public ResponseEntity<EducationResponse> createEducation(@Valid @RequestBody EducationRequest educationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(educationService.createEducation(educationRequest));
    }

    @GetMapping(PathName.PATH_ID)
    public ResponseEntity<EducationResponse> getEducation(@PathVariable Long id) {
        return ResponseEntity.ok(educationService.getEducation(id));
    }

    @PutMapping(PathName.PATH_ID)
    public ResponseEntity<EducationResponse> updateEducation(@PathVariable Long id, @Valid @RequestBody EducationRequest educationRequest) {
        return ResponseEntity.ok(educationService.updateEducation(id, educationRequest));
    }

    @DeleteMapping(PathName.PATH_ID)
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.ok().build();
    }

}
