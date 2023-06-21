package com.devhong.reservation.service;

import com.devhong.reservation.model.Store;
import com.devhong.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BaseService {

    private final StoreRepository storeRepository;

    public List<Store> searchStore(String keyword) {
        return storeRepository.findByStoreNameContainingIgnoreCase(keyword);
    }
}
