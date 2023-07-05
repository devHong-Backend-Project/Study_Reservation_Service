package com.devhong.reservation.controller;

import com.devhong.reservation.dto.StoreDto;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.service.BaseService;
import com.devhong.reservation.type.CacheKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/*
    권한 없이 사용할 수 있는 api
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class BaseController {

    private final BaseService baseService;

    /*
        상점 검색 기능
        1. 찾고자하는 상점이름 검색 (이름의 일부만 입력해도 검색결과 나옴)
        2. Pageable를 사용해서 리스트 형태로 리턴.
     */
    @GetMapping("/store")
    public ResponseEntity<?> searchStore(@RequestParam("search") String search, Pageable pageable) {
        Page<Store> stores = baseService.searchStore(search, pageable);
        return ResponseEntity.ok(
                stores.stream().map(StoreDto.SearchResponse::fromEntity).collect(Collectors.toList()));
    }

    /*
        상점 상세 정보
        1. 상점 검색으로 얻은 storeId를 가지고 해당 상점의 세부정보 검색
        2. DetailResponse Dto 객체를 리턴
     */
    @GetMapping("/store/detail/{storeId}")
    public ResponseEntity<?> getDetail(@PathVariable Long storeId) {
        Store store = baseService.getDetail(storeId);
        return ResponseEntity.ok(StoreDto.DetailResponse.fromEntity(store));
    }
}
