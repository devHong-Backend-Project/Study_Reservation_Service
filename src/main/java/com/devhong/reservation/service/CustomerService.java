package com.devhong.reservation.service;

import com.devhong.reservation.dto.StoreDto;
import com.devhong.reservation.exception.CustomErrorCode;
import com.devhong.reservation.exception.CustomException;
import com.devhong.reservation.model.Reservation;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.model.User;
import com.devhong.reservation.repository.ReservationRepository;
import com.devhong.reservation.repository.StoreRepository;
import com.devhong.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final int MINUTES_PRIOR_TO_VISIT = 10;

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    /*
        예약하기
        1. validateReservationTime : 현재시간이 만약 예약시간 10분전(도착해야하는 시간)보다 뒤라면 예약불가
        2. Customer 유저 확인
        3. 예약하려는 상점 유무 확인
        4. 예약
     */
    public Reservation addReservation(StoreDto.Reserve reserve) {
        validateReservationTime(reserve.getReservationTime());

        User user = userRepository.findById(reserve.getUserId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(reserve.getStoreId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.STORE_NOT_FOUND));

        return reservationRepository.save(reserve.toEntity(user, store));
    }

    private void validateReservationTime(String reservationTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (LocalDateTime.now().isAfter(LocalDateTime.parse(reservationTime,formatter)
                .minusMinutes(MINUTES_PRIOR_TO_VISIT))) {
            throw new CustomException(CustomErrorCode.UNABLE_TO_RESERVATION);
        }
    }
}
