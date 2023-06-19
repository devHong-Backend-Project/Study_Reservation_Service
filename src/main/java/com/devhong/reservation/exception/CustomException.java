package com.devhong.reservation.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{

    private CustomErrorCode errorCode;
    private String errorMessage;
    private HttpStatus status;

    public CustomException(CustomErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
        this.status = errorCode.getHttpStatus();
    }
}
