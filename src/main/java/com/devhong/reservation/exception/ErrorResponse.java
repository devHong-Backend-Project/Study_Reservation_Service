package com.devhong.reservation.exception;

import lombok.Builder;
import lombok.Data;

/*
    커스텀 예외 발생시 서버 응답 Dto
 */
@Data
@Builder
public class ErrorResponse {
    private int statusCode;
    private CustomErrorCode errorCode;
    private String errorMessage;
}
