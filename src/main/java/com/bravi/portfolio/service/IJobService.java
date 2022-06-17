package com.bravi.portfolio.service;

import com.bravi.portfolio.dto.JobRequest;
import com.bravi.portfolio.dto.JobResponse;

import java.util.List;

public interface IJobService {

    List<JobResponse> getAllJobs();
    JobResponse getJob(Long id);
    JobResponse createJob(JobRequest job);
    JobResponse updateJob(Long id, JobRequest job);
    void deleteJob(Long id);

}
