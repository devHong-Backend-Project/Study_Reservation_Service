package com.devhong.reservation.service;

import com.devhong.reservation.exception.CustomErrorCode;
import com.devhong.reservation.exception.CustomException;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BaseService {

    private final StoreRepository storeRepository;

    /*
        상점 검색 기능
        1. 키워드를 클라이언트로부터 받아와서 키워드가 포함된 상점이름들을 모두 가져와서 보여줌
     */
    public Page<Store> searchStore(String keyword, Pageable pageable) {
        return storeRepository.findByStoreNameContainingIgnoreCase(keyword, pageable);
    }

    /*
        상점 상세정보 보기
        1. 상점id를 받아와서 그 상점의 상세정보를 보여줌
     */
    public Store getDetail(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.STORE_NOT_FOUND));
    }
}
