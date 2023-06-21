package com.devhong.reservation.service;

import com.devhong.reservation.dto.StoreDto;
import com.devhong.reservation.exception.CustomErrorCode;
import com.devhong.reservation.exception.CustomException;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.model.User;
import com.devhong.reservation.repository.StoreRepository;
import com.devhong.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public Store addStore(StoreDto.Registration store) {
        User user = userRepository.findById(store.getUserId())
                .orElseThrow(()-> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        if (storeRepository.existsByStoreNameAndLocation(store.getStoreName(), store.getLocation())) {
            throw new CustomException(CustomErrorCode.STORE_ALREADY_EXISTS);
        }

        return storeRepository.save(store.toEntity(user));
    }
}
