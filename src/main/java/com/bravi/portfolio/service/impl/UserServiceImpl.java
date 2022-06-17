package com.bravi.portfolio.service.impl;

import com.bravi.portfolio.dto.UserRequest;
import com.bravi.portfolio.dto.UserResponse;
import com.bravi.portfolio.service.IUserService;
import com.bravi.portfolio.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse login(UserRequest userRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return UserResponse.builder().jwt(JwtUtil.generateToken(userDetails)).build();
    }

}
