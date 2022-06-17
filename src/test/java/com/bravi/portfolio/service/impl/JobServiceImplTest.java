package com.bravi.portfolio.service.impl;

import com.bravi.portfolio.dto.JobRequest;
import com.bravi.portfolio.dto.JobResponse;
import com.bravi.portfolio.entity.Job;
import com.bravi.portfolio.mapper.JobMapperImpl;
import com.bravi.portfolio.repository.JobRepository;
import com.bravi.portfolio.util.BuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobMapperImpl jobMapper;

    @InjectMocks
    private JobServiceImpl jobService;

    Job job;

    @BeforeEach
    void setUp() {
        job = BuilderUtil.buildJob(null);
    }

    @Test
    void getAllJobs() {
        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDtoList(List.of(job))).thenReturn(List.of(new JobResponse()));

        jobService.getAllJobs();

        verify(jobRepository, atLeastOnce()).findAll();
        verify(jobMapper, atLeastOnce()).toDtoList(List.of(job));
    }

    @Test
    void getJob() {
        when(jobRepository.findById(1L)).thenReturn(Optional.ofNullable(job));
        when(jobMapper.toDto(job)).thenReturn(new JobResponse());

        jobService.getJob(1L);

        verify(jobRepository, atLeastOnce()).findById(1L);
        verify(jobMapper, atLeastOnce()).toDto(job);
    }

    @Test
    void createJob() {
        when(jobRepository.save(job)).thenReturn(job);
        when(jobMapper.toEntity(any(JobRequest.class))).thenReturn(job);
        when(jobMapper.toDto(job)).thenReturn(new JobResponse());

        jobService.createJob(new JobRequest());

        verify(jobRepository, atLeastOnce()).save(job);
        verify(jobMapper, atLeastOnce()).toEntity(new JobRequest());
        verify(jobMapper, atLeastOnce()).toDto(job);
    }

    @Test
    void updateJob() {
        when(jobRepository.findById(1L)).thenReturn(Optional.ofNullable(job));
        when(jobRepository.save(job)).thenReturn(job);
        when(jobMapper.toEntity(any(Job.class), any(JobRequest.class))).thenReturn(job);
        when(jobMapper.toDto(job)).thenReturn(new JobResponse());

        jobService.updateJob(1L, new JobRequest());

        verify(jobRepository, atLeastOnce()).findById(1L);
        verify(jobRepository, atLeastOnce()).save(job);
        verify(jobMapper, atLeastOnce()).toEntity(job, new JobRequest());
        verify(jobMapper, atLeastOnce()).toDto(job);
    }

    @Test
    void deleteJob() {
        when(jobRepository.existsById(1L)).thenReturn(true);

        jobService.deleteJob(1L);

        verify(jobRepository, atLeastOnce()).existsById(1L);
        verify(jobRepository, atLeastOnce()).deleteById(1L);
    }
}