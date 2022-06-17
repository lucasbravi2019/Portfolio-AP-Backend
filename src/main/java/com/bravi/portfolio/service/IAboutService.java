package com.bravi.portfolio.service;

import com.bravi.portfolio.dto.AboutRequest;
import com.bravi.portfolio.dto.AboutResponse;
import com.bravi.portfolio.entity.About;

public interface IAboutService {

    AboutResponse getAbout();
    AboutResponse createAbout(AboutRequest aboutRequest);
    AboutResponse updateAbout(Long id, AboutRequest aboutRequest);

}
