package com.devhong.reservation.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int statusCode;
    private CustomErrorCode errorCode;
    private String errorMessage;
}
