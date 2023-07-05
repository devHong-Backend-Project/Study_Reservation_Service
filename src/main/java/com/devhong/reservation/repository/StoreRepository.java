package com.devhong.reservation.repository;

import com.devhong.reservation.model.Store;
import com.devhong.reservation.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findByStoreNameContainingIgnoreCase(String storeName, Pageable pageable);

    Optional<Store> findByIdAndUser(Long storeId, User user);

    boolean existsByStoreNameAndLocation(String storeName, String location);
}
