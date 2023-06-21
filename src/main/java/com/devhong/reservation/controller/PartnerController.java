package com.devhong.reservation.controller;

import com.devhong.reservation.dto.StoreDto;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
@PreAuthorize("hasRole('PARTNER')")
public class PartnerController {

    private final PartnerService partnerService;

    @PostMapping("/store/register")
    public ResponseEntity<?> addStore(@RequestBody @Valid StoreDto.Registration request) {
        Store store = partnerService.resgisterStore(request);
        return ResponseEntity.ok(StoreDto.RegistrationResponse.fromEntity(store));
    }
}
