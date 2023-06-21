package com.devhong.reservation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    /*
        api 접근 권한 설정으로 요청 제한이 되었을때 예외 리스폰스
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .errorCode(CustomErrorCode.ACCESS_DENIED)
                .errorMessage(CustomErrorCode.ACCESS_DENIED.getErrorMessage())
                .build();
        log.error("Access Denied: {}", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
