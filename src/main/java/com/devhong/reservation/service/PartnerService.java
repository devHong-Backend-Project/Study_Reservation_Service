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

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /*
        상점 등록하기
        1. 파트너 회원 찾기 (회원이 없으면 예외발생)
        2. 상점의 이름과 위치가 같으면 이미 등록된 상점이라고 판단. 예외발생
        3. 상점 등록
     */
    public Store addStore(StoreDto.Registration store) {
        User user = userRepository.findById(store.getUserId())
                .orElseThrow(()-> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        if (storeRepository.existsByStoreNameAndLocation(store.getStoreName(), store.getLocation())) {
            throw new CustomException(CustomErrorCode.STORE_ALREADY_EXISTS);
        }

        return storeRepository.save(store.toEntity(user));
    }

    /*
        예약 확정하기
        1. 확정할 예약 불러오기 (예약이 존재하지 않으면 예외 발생)
        2. validateReservation(이미 확정된 예약인지, 취소된 예약인지 확인 후 예외발생)
        3. 예약 확정
     */
    public Reservation confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.RESERVATION_NOT_FOUND));

        validateReservation(reservation);

        reservation.setConfirmed(true);
        reservation.setReservedAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    private void validateReservation(Reservation reservation) {
        if (reservation.isConfirmed()){
            throw new CustomException(CustomErrorCode.RESERVATION_ALREADY_CONFIRMED);
        }

        if (reservation.isCanceled()){
            throw new CustomException(CustomErrorCode.RESERVATION_IS_CANCELED);
        }
    }

    public Reservation cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.RESERVATION_NOT_FOUND));

        reservation.setCanceled(true);
        reservation.setCanceledAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }
}
