package com.bravi.portfolio.controller;

import com.bravi.portfolio.dto.AboutRequest;
import com.bravi.portfolio.dto.AboutResponse;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.service.impl.AboutServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(PathName.ABOUT)
public class AboutController {

    private final AboutServiceImpl aboutService;

    @GetMapping
    public ResponseEntity<AboutResponse> getAbout() {
        return ResponseEntity.ok(aboutService.getAbout());
    }

    @PostMapping
    public ResponseEntity<AboutResponse> createAbout(@Valid @RequestBody AboutRequest aboutRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aboutService.createAbout(aboutRequest));
    }

    @PutMapping(PathName.PATH_ID)
    public ResponseEntity<AboutResponse> updateAbout(
            @PathVariable Long id,
            @Valid @RequestBody AboutRequest aboutRequest
    ) {
        return ResponseEntity.ok(aboutService.updateAbout(id, aboutRequest));
    }

}
