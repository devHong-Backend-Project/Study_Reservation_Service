package com.devhong.reservation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleAccountException(CustomException e) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(e.getStatus().value())
                .errorCode(e.getErrorCode())
                .errorMessage(e.getErrorMessage())
                .build();
        log.error("{} is occurred.",e.getErrorCode());
        return new ResponseEntity<>(response, e.getStatus());
    }
}
