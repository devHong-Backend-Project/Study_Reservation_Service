package com.devhong.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST ,"이미 회원가입이 되어있는 아이디입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디입니다."),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 상점입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    STORE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 등록된 상점입니다."),
    UNABLE_TO_RESERVATION(HttpStatus.BAD_REQUEST, "예약이 불가능합니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "요청 접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

}
