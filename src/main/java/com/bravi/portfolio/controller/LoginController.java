package com.bravi.portfolio.controller;

import com.bravi.portfolio.dto.UserRequest;
import com.bravi.portfolio.dto.UserResponse;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(PathName.LOGIN)
public class LoginController {

    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.login(userRequest));
    }

}
