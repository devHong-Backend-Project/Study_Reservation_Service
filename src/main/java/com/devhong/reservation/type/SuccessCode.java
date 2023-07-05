package com.devhong.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    SUCCESS_UPDATE_STORE_DETAIL(HttpStatus.OK,"상점 정보수정 완료");

    private final HttpStatus statusCode;
    private final String message;
}
