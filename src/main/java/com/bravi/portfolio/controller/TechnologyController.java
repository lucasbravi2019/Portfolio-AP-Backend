package com.bravi.portfolio.controller;

import com.bravi.portfolio.dto.TechnologyRequest;
import com.bravi.portfolio.dto.TechnologyResponse;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.service.impl.TechnologyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(PathName.TECHNOLOGY)
public class TechnologyController {

    private final TechnologyServiceImpl technologyService;

    @GetMapping
    public ResponseEntity<List<TechnologyResponse>> getAllTechnologies() {
        return ResponseEntity.ok(technologyService.getAllTechnologies());
    }

    @GetMapping(PathName.PATH_ID)
    public ResponseEntity<TechnologyResponse> getTechnology(@PathVariable Long id) {
        return ResponseEntity.ok(technologyService.getTechnology(id));
    }

    @PostMapping
    public ResponseEntity<TechnologyResponse> createTechnology(@Valid @RequestBody TechnologyRequest technology) {
        return ResponseEntity.status(HttpStatus.CREATED).body(technologyService.createTechnology(technology));
    }

    @PutMapping(PathName.PATH_ID)
    public ResponseEntity<TechnologyResponse> updateTechnology(@PathVariable Long id, @Valid @RequestBody TechnologyRequest technology) {
        return ResponseEntity.ok(technologyService.updateTechnology(id, technology));
    }

    @DeleteMapping(PathName.PATH_ID)
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        technologyService.deleteTechnology(id);
        return ResponseEntity.ok().build();
    }


}
