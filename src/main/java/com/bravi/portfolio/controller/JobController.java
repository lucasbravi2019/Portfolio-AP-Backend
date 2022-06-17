package com.bravi.portfolio.controller;

import com.bravi.portfolio.dto.JobRequest;
import com.bravi.portfolio.dto.JobResponse;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.service.IJobService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(PathName.JOB)
public class JobController {

    private final IJobService jobService;

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping(PathName.PATH_ID)
    public ResponseEntity<JobResponse> getJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJob(id));
    }


    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest job) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(job));
    }

    @PutMapping(PathName.PATH_ID)
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @Valid @RequestBody JobRequest job) {
        return ResponseEntity.ok(jobService.updateJob(id, job));
    }

    @DeleteMapping(PathName.PATH_ID)
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok().build();
    }


}
