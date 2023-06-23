package com.devhong.reservation.controller;

import com.devhong.reservation.dto.ReservationDto;
import com.devhong.reservation.dto.StoreDto;
import com.devhong.reservation.model.Reservation;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/*
    ROLE_PARTNER 권한을 가진 회원만 사용 가능한 api
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
@PreAuthorize("hasRole('PARTNER')")
public class PartnerController {

    private final PartnerService partnerService;

    /*
        상점 등록
     */
    @PostMapping("/store/register")
    public ResponseEntity<?> registerStore(@RequestBody @Valid StoreDto.Registration request) {
        Store store = partnerService.addStore(request);
        return ResponseEntity.ok(StoreDto.RegistrationResponse.fromEntity(store));
    }

    /*
        예약 승인
     */
    @PostMapping("/store/confirm/{reservationId}")
    public ResponseEntity<?> confirmReservation(@PathVariable Long reservationId){
        Reservation reservation = partnerService.confirmReservation(reservationId);
        return ResponseEntity.ok(ReservationDto.ConfirmResponse.fromEntity(reservation));
    }

    /*
        예약 거절(취소)
     */
    @PostMapping("/store/cancel/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId) {
        Reservation reservation = partnerService.cancelReservation(reservationId);
        return ResponseEntity.ok(ReservationDto.CancelResponse.fromEntity(reservation));
    }

    /*
        예약 목록 확인
        - 상점id와 username을 받아서 user가 해당상점에 예약한 내용들을 보여줌.
     */
    @GetMapping("/store/{storeId}/reservation")
    public ResponseEntity<?> getReservations(@PathVariable Long storeId, @RequestParam("username") String username) {
        List<Reservation> reservations = partnerService.getReservations(storeId, username);
        return ResponseEntity.ok(reservations.stream()
                .map(ReservationDto.ReservationResponse::fromEntity).collect(Collectors.toList()));
    }

    /*
        방문 확인
        1. 위의 getReservations api로 알아낸 reservationId를 입력받아 방문 확인 진행
     */
    @PostMapping("/store/visit/{reservationId}")
    public ResponseEntity<?> checkVisit(@PathVariable Long reservationId) {
        Reservation reservation = partnerService.checkVisit(reservationId);
        return ResponseEntity.ok(ReservationDto.VisitResponse.fromEntity(reservation));
    }

}
