package com.devhong.reservation.service;

import com.devhong.reservation.dto.ReservationDto;
import com.devhong.reservation.dto.ReviewDto;
import com.devhong.reservation.exception.CustomErrorCode;
import com.devhong.reservation.exception.CustomException;
import com.devhong.reservation.model.Reservation;
import com.devhong.reservation.model.Review;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.model.User;
import com.devhong.reservation.repository.ReservationRepository;
import com.devhong.reservation.repository.ReviewRepository;
import com.devhong.reservation.repository.StoreRepository;
import com.devhong.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private static final int MINUTES_PRIOR_TO_VISIT = 10;
    private static final int RESERVATION_LIMIT = 50;

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    /*
        예약하기
        1. Customer 유저 확인
        2. 예약하려는 상점 유무 확인
        3. validateReservationTime : 예약이 가능한 시간인지 체크
        4. 예약
     */
    public Reservation addReservation(ReservationDto.Reserve reserve) {

        User user = userRepository.findByUsername(reserve.getUserName())
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(reserve.getStoreId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.STORE_NOT_FOUND));

        validateReservationTime(reserve.getReservationTime(), user.getId(), store.getId());

        return reservationRepository.save(reserve.toEntity(user, store));
    }

    /*
        예약시간 유효성 체크
        1. 현재시간이 만약 예약시간 10분전(도착해야하는 시간)보다 뒤라면 예약불가,
        2. 유저가 해당시간에 이미 예약했으면 예약 불가,
        3. 해당 예약시간에 예약건수가 50건 이상일때 예약불가
     */
    private void validateReservationTime(String reservationTime, Long userId, Long storeId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formattedDateTime = LocalDateTime.parse(reservationTime, formatter);

        if (LocalDateTime.now().isAfter(formattedDateTime.minusMinutes(MINUTES_PRIOR_TO_VISIT))) {
            throw new CustomException(CustomErrorCode.UNABLE_TO_RESERVATION);
        }

        if (reservationRepository.existsByUserIdAndStoreIdAndReservationTime(
                userId, storeId, formattedDateTime)) {
            throw new CustomException(CustomErrorCode.RESERVATION_ALREADY_EXISTS);
        }

        if (reservationRepository.countByStoreIdAndIsCanceledAndReservationTime(
                storeId, false, formattedDateTime) >= RESERVATION_LIMIT) {
            throw new CustomException(CustomErrorCode.RESERVATION_IS_FULL);
        }
    }

    /*
        리뷰 추가
        1. 유저, 상점 유무 확인
        2. 리뷰를 쓸 수 있는 지 유효성 검증
        3. 리뷰 DB에 저장, 저장한 Review 엔티티 리턴
     */
    public Review addReview(ReviewDto.ReviewRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.STORE_NOT_FOUND));

        validateReview(user, store);

        return reviewRepository.save(request.toEntity(user, store));
    }

    /*
        리뷰 유효성 검증
        1. 실제 방문을 한 상점인지 확인
     */
    private void validateReview(User user, Store store) {
        if (!reservationRepository.existsByUserAndStoreAndIsVisited(user, store, true)) {
            throw new CustomException(CustomErrorCode.REVIEW_NOT_ALLOWED);
        }
    }
}
