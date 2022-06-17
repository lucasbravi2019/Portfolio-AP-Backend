package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.*;
import com.bravi.portfolio.entity.Job;
import com.bravi.portfolio.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bravi.portfolio.util.MapperUtil.*;

@AllArgsConstructor
@Component
public class JobMapperImpl implements IMapper<Job, JobRequest, JobResponse> {

    private final PersonaRepository personaRepository;

    @Override
    public Job toEntity(JobRequest request) {
        return Job.builder()
                .companyName(request.getCompanyName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .jobDescription(request.getJobDescription())
                .jobRole(request.getJobRole())
                .persona(
                        mapNullableObjectWithFilter(
                                request.getPersonaId(),
                                personaRepository::existsById,
                                personaRepository::getReferenceById
                        )
                )
                .build();
    }

    @Override
    public Job toEntity(Job job, JobRequest request) {
        job.setStartDate(request.getStartDate());
        job.setEndDate(request.getEndDate());
        job.setJobDescription(request.getJobDescription());
        job.setJobRole(request.getJobRole());
        job.setCompanyName(request.getCompanyName());
        job.setPersona(
                mapNullableObjectWithFilter(
                        request.getPersonaId(),
                        personaRepository::existsById,
                        personaRepository::getReferenceById
                )
        );
        return job;
    }

    @Override
    public JobResponse toDto(Job entity) {
        return RelationshipMapper.mapJobResponse(entity, null);
    }

    @Override
    public List<JobResponse> toDtoList(List<Job> list) {
        return streamNullableList(list, this::toDto);
    }
}
