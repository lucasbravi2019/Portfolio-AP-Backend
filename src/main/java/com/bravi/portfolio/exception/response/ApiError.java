package com.bravi.portfolio.exception.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiError<T> {

    T errors;
    HttpStatus status;

}
