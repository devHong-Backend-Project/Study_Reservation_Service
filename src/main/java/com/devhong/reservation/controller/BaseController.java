package com.devhong.reservation.controller;

import com.devhong.reservation.dto.StoreDto;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BaseController {

    private final BaseService baseService;

    @GetMapping("/store")
    public ResponseEntity<?> searchStore(@RequestParam("search") String search) {
        List<Store> stores = baseService.searchStore(search);

        return ResponseEntity.ok(
                stores.stream().map(StoreDto.SearchResponse::fromEntity).collect(Collectors.toList()));
    }
}
