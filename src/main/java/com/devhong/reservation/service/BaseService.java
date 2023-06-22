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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BaseService {

    private final StoreRepository storeRepository;

    public Page<Store> searchStore(String keyword, Pageable pageable) {
        return storeRepository.findByStoreNameContainingIgnoreCase(keyword, pageable);
    }

    public Store getDetail(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.STORE_NOT_FOUND));
    }
}
