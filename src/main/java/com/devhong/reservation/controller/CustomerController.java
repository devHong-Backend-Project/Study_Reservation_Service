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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
@PostAuthorize("hasRole('CUSTOMER')")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/store/reserve")
    public ResponseEntity<?> reserveStore(@RequestBody @Valid ReservationDto.Reserve request) {
        Reservation reservation = customerService.addReservation(request);
        return ResponseEntity.ok(ReservationDto.ReservationResponse.fromEntity(reservation));
    }

    @PostMapping("/store/review")
    public ResponseEntity<?> createReview(@RequestBody @Valid ReviewDto.ReviewRequest request) {
        Review review = customerService.addReview(request);
        return ResponseEntity.ok(ReviewDto.ReviewResponse.fromEntity(review));
    }
}
