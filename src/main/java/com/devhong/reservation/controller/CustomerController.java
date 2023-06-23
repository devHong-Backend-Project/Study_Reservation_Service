package com.devhong.reservation.controller;

import com.devhong.reservation.dto.ReservationDto;
import com.devhong.reservation.dto.ReviewDto;
import com.devhong.reservation.model.Reservation;
import com.devhong.reservation.model.Review;
import com.devhong.reservation.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/*
    ROLE_CUSTOMER 권한을 가진 회원만 사용가능한 api
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
@PostAuthorize("hasRole('CUSTOMER')")
public class CustomerController {

    private final CustomerService customerService;

    /*
        예약하기
        1. 클라이언트로 부터 예약에 필요한 정보를 입력받아 예약 진행
        2. 예약이 가능하면 예약진행후 예약 정보를 ReservationResponse Dto 객체로 리턴
     */
    @PostMapping("/store/reserve")
    public ResponseEntity<?> reserveStore(@RequestBody @Valid ReservationDto.Reserve request) {
        Reservation reservation = customerService.addReservation(request);
        return ResponseEntity.ok(ReservationDto.ReservationResponse.fromEntity(reservation));
    }

    /*
        리뷰 작성하기
        1. 클라이언트로 부터 리뷰 작성에 필요한 정보들 입력받아 리뷰 작성 진행
        2. 리뷰 작성에 성공하면, Review Entity객체를 ReviewResponse Dto 객체로 변환후 리턴
     */
    @PostMapping("/store/review")
    public ResponseEntity<?> createReview(@RequestBody @Valid ReviewDto.ReviewRequest request) {
        Review review = customerService.addReview(request);
        return ResponseEntity.ok(ReviewDto.ReviewResponse.fromEntity(review));
    }
}
