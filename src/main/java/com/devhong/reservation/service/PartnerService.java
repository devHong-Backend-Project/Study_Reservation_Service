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
import java.util.List;

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

    /*
        예약 취소
        1. 존재하는 예약인지 확인
        2. 취소 가능한 예약인지 validate(이미 취소된 예약인지, 이미 승인된 예약인지 체크)
        3. 취소처리 후에 DB에 저장
     */
    public Reservation cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.RESERVATION_NOT_FOUND));

        validateCancel(reservation);

        reservation.setCanceled(true);
        reservation.setCanceledAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    /*
        예약 취소 유효성 검증
        1. 이미 취소된 예약이면 취소 불가
        2. 이미 예약 승인이 된 예약은 취소 불가.
     */
    private void validateCancel(Reservation reservation) {
        if (reservation.isCanceled()) {
            throw new CustomException(CustomErrorCode.RESERVATION_IS_CANCELED);
        }

        if (reservation.isConfirmed()){
            throw new CustomException(CustomErrorCode.RESERVATION_ALREADY_CONFIRMED);
        }
    }

    /*
        방문확인
        1. 존재하는 예약인지 확인
        2. 방문 확인 유효성 validate 체크
        3. 방문처리 후 DB에 저장
     */
    public Reservation checkVisit(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.RESERVATION_NOT_FOUND));

        validateVisit(reservation);

        reservation.setVisited(true);

        return reservationRepository.save(reservation);
    }

    /*
        방문 확인 유효성
        1. 예약시간 10분전에 도착 못하면 방문 확인 불가
        2. 아직 승인되지 않은 예약은 방문 확인 불가
        3. 취소된 예약이면 방문 확인 불가
        4. 이미 방문 처리가 된 예약일때 예외 발생
     */
    private void validateVisit(Reservation reservation) {
        if (LocalDateTime.now().isAfter(reservation.getReservationTime().minusMinutes(10))) {
            throw new CustomException(CustomErrorCode.UNABLE_TO_CHECK);
        }

        if (!reservation.isConfirmed()) {
            throw new CustomException(CustomErrorCode.RESERVATION_NOT_CONFIRMED);
        }

        if (reservation.isCanceled()) {
            throw new CustomException(CustomErrorCode.RESERVATION_IS_CANCELED);
        }

        if (reservation.isVisited()) {
            throw new CustomException(CustomErrorCode.VISIT_ALREADY_CHECKED);
        }
    }

    /*
        예약 목록 보기
        1. 예약자 확인. (없는 유저면 예외 발생)
        2. 예약자명과 상점Id를 통해 예약 조회. 리스트로 리턴
     */
    public List<Reservation> getReservations(Long storeId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        return reservationRepository.findByStoreIdAndUser(storeId, user);
    }
}
