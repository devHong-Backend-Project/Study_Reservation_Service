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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
@PreAuthorize("hasRole('PARTNER')")
public class PartnerController {

    private final PartnerService partnerService;

    @PostMapping("/store/register")
    public ResponseEntity<?> registerStore(@RequestBody @Valid StoreDto.Registration request) {
        Store store = partnerService.addStore(request);
        return ResponseEntity.ok(StoreDto.RegistrationResponse.fromEntity(store));
    }

    @PostMapping("/store/confirm/{reservationId}")
    public ResponseEntity<?> confirmReservation(@PathVariable Long reservationId){
        Reservation reservation = partnerService.confirmReservation(reservationId);
        return ResponseEntity.ok(ReservationDto.ConfirmResponse.fromEntity(reservation));
    }

    @PostMapping("/store/cancel/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId) {
        Reservation reservation = partnerService.cancelReservation(reservationId);
        return ResponseEntity.ok(ReservationDto.CancelResponse.fromEntity(reservation));
    }
}
