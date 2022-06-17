package com.bravi.portfolio.service.impl;

import com.bravi.portfolio.dto.TechnologyRequest;
import com.bravi.portfolio.dto.TechnologyResponse;
import com.bravi.portfolio.entity.Technology;
import com.bravi.portfolio.mapper.TechnologyMapperImpl;
import com.bravi.portfolio.repository.TechnologyRepository;
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
class TechnologyServiceImplTest {

    @Mock
    private TechnologyRepository technologyRepository;

    @Mock
    private TechnologyMapperImpl technologyMapper;

    @InjectMocks
    private TechnologyServiceImpl technologyService;

    Technology technology;

    @BeforeEach
    void setUp() {
        technology = BuilderUtil.buildTechnology(null);
    }

    @Test
    void getAllTechnologies() {
        when(technologyRepository.findAll()).thenReturn(List.of(technology));
        when(technologyMapper.toDtoList(List.of(technology))).thenReturn(List.of(new TechnologyResponse()));

        technologyService.getAllTechnologies();

        verify(technologyRepository, atLeastOnce()).findAll();
        verify(technologyMapper, atLeastOnce()).toDtoList(List.of(technology));
    }

    @Test
    void getTechnology() {
        when(technologyRepository.findById(1L)).thenReturn(Optional.ofNullable(technology));
        when(technologyMapper.toDto(technology)).thenReturn(new TechnologyResponse());

        technologyService.getTechnology(1L);

        verify(technologyRepository, atLeastOnce()).findById(1L);
        verify(technologyMapper, atLeastOnce()).toDto(technology);
    }

    @Test
    void createTechnology() {
        when(technologyRepository.save(technology)).thenReturn(technology);
        when(technologyMapper.toEntity(any(TechnologyRequest.class))).thenReturn(technology);
        when(technologyMapper.toDto(technology)).thenReturn(new TechnologyResponse());

        technologyService.createTechnology(new TechnologyRequest());

        verify(technologyRepository, atLeastOnce()).save(technology);
        verify(technologyMapper, atLeastOnce()).toEntity(new TechnologyRequest());
        verify(technologyMapper, atLeastOnce()).toDto(technology);
    }

    @Test
    void updateTechnology() {
        when(technologyRepository.findById(1L)).thenReturn(Optional.ofNullable(technology));
        when(technologyRepository.save(technology)).thenReturn(technology);
        when(technologyMapper.toEntity(any(Technology.class), any(TechnologyRequest.class))).thenReturn(technology);
        when(technologyMapper.toDto(technology)).thenReturn(new TechnologyResponse());

        technologyService.updateTechnology(1L, new TechnologyRequest());

        verify(technologyRepository, atLeastOnce()).findById(1L);
        verify(technologyRepository, atLeastOnce()).save(technology);
        verify(technologyMapper, atLeastOnce()).toEntity(technology, new TechnologyRequest());
        verify(technologyMapper, atLeastOnce()).toDto(technology);
    }

    @Test
    void deleteTechnology() {
        when(technologyRepository.existsById(1L)).thenReturn(true);

        technologyService.deleteTechnology(1L);

        verify(technologyRepository, atLeastOnce()).existsById(1L);
        verify(technologyRepository, atLeastOnce()).deleteById(1L);
    }
}