package com.devhong.reservation.dto;

import com.devhong.reservation.type.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class SuccessDto {

    @Builder
    @Data
    public static class Response{
        private int statusCode;
        private String message;

        public static Response toDto(SuccessCode successCode) {
            return Response.builder()
                    .statusCode(successCode.getStatusCode().value())
                    .message(successCode.getMessage())
                    .build();
    }

    }
}
