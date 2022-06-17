package com.bravi.portfolio.service;

import com.bravi.portfolio.dto.UserRequest;
import com.bravi.portfolio.dto.UserResponse;

public interface IUserService {

    UserResponse login(UserRequest userRequest);

}
