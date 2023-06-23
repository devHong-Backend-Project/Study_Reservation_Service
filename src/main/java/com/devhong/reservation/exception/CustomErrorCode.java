package com.devhong.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
    커스텀 exception
 */
@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST ,"이미 회원가입이 되어있는 아이디입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디입니다."),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 상점입니다."),
    STORE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 등록된 상점입니다."),
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 예약입니다."),
    RESERVATION_ALREADY_CONFIRMED(HttpStatus.BAD_REQUEST, "이미 승인된 예약입니다."),
    RESERVATION_NOT_CONFIRMED(HttpStatus.BAD_REQUEST, "승인되지 않은 예약입니다."),
    RESERVATION_IS_CANCELED(HttpStatus.BAD_REQUEST, "취소된 예약입니다."),
    RESERVATION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 예약하신 시간입니다."),
    RESERVATION_IS_FULL(HttpStatus.BAD_REQUEST, "예약이 모두 완료된 시간입니다."),
    REVIEW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "리뷰 작성이 불가능합니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    UNABLE_TO_RESERVATION(HttpStatus.BAD_REQUEST, "예약이 불가능합니다."),
    UNABLE_TO_CHECK(HttpStatus.BAD_REQUEST, "도착 확인이 불가능합니다."),
    VISIT_ALREADY_CHECKED(HttpStatus.BAD_REQUEST, "이미 방문 처리 되었습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "요청 접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

}
