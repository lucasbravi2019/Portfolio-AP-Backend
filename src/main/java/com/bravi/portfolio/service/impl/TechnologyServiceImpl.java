package com.bravi.portfolio.service.impl;


import com.bravi.portfolio.dto.TechnologyRequest;
import com.bravi.portfolio.dto.TechnologyResponse;
import com.bravi.portfolio.entity.Technology;
import com.bravi.portfolio.mapper.TechnologyMapperImpl;
import com.bravi.portfolio.repository.TechnologyRepository;
import com.bravi.portfolio.service.ITechnologyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TechnologyServiceImpl implements ITechnologyService {

    private final TechnologyRepository technologyRepository;
    private final TechnologyMapperImpl technologyMapper;

    @Override
    public List<TechnologyResponse> getAllTechnologies() {
        return technologyMapper.toDtoList(technologyRepository.findAll());
    }

    @Override
    public TechnologyResponse getTechnology(Long id) {
        return technologyMapper.toDto(
                technologyRepository.findById(id).orElseThrow(() -> new RuntimeException("Technology not found."))
        );
    }

    @Override
    public TechnologyResponse createTechnology(TechnologyRequest technology) {
        return technologyMapper.toDto(technologyRepository.save(technologyMapper.toEntity(technology)));
    }

    @Override
    public TechnologyResponse updateTechnology(Long id, TechnologyRequest technology) {
        Technology entity = technologyRepository.findById(id).orElseThrow(() -> new RuntimeException("Technology not found."));
        return technologyMapper.toDto(technologyRepository.save(technologyMapper.toEntity(entity, technology)));
    }

    @Override
    public void deleteTechnology(Long id) {
        if (!technologyRepository.existsById(id)) {
            throw new RuntimeException("Technology not found.");
        }
        technologyRepository.deleteById(id);
    }
}
