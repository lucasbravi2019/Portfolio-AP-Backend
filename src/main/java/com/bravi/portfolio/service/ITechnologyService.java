package com.bravi.portfolio.service;

import com.bravi.portfolio.dto.TechnologyRequest;
import com.bravi.portfolio.dto.TechnologyResponse;

import java.util.List;

public interface ITechnologyService {

    List<TechnologyResponse> getAllTechnologies();
    TechnologyResponse getTechnology(Long id);
    TechnologyResponse createTechnology(TechnologyRequest technology);
    TechnologyResponse updateTechnology(Long id, TechnologyRequest technology);
    void deleteTechnology(Long id);

}
