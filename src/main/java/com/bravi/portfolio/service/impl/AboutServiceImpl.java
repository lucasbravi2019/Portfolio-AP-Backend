package com.bravi.portfolio.service.impl;

import com.bravi.portfolio.dto.AboutRequest;
import com.bravi.portfolio.dto.AboutResponse;
import com.bravi.portfolio.entity.About;
import com.bravi.portfolio.mapper.AboutMapperImpl;
import com.bravi.portfolio.repository.AboutRepository;
import com.bravi.portfolio.service.IAboutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AboutServiceImpl implements IAboutService {

    private final AboutRepository aboutRepository;
    private final AboutMapperImpl aboutMapper;

    @Override
    public AboutResponse getAbout() {
        return aboutMapper.toDto(aboutRepository.findFirstByOrderById()
                .orElseThrow(() -> new RuntimeException("About not found.")));
    }

    @Override
    public AboutResponse createAbout(AboutRequest aboutRequest) {
        if (aboutRepository.findAll().size() > 0) throw new RuntimeException("About already exists.");
        return aboutMapper.toDto(aboutRepository.save(aboutMapper.toEntity(aboutRequest)));
    }

    @Override
    public AboutResponse updateAbout(Long id, AboutRequest aboutRequest) {
        About entity = aboutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("About not found."));

        return aboutMapper.toDto(aboutRepository.save(aboutMapper.toEntity(entity, aboutRequest)));
    }
}
