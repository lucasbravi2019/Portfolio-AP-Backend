package com.bravi.portfolio.exception.handler;

import com.bravi.portfolio.exception.response.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(
                        String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                );
        ex.getBindingResult().getGlobalErrors()
                .forEach(error -> errors.add(
                        String.format("%s: %s", error.getObjectName(), error.getDefaultMessage()))
                );
        ApiError<List<String>> apiError = ApiError.<List<String>>builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.builder()
                        .errors(ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build());
    }

}
