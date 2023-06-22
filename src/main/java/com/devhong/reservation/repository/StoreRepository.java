package com.devhong.reservation.repository;

import com.devhong.reservation.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findByStoreNameContainingIgnoreCase(String storeName, Pageable pageable);

    boolean existsByStoreNameAndLocation(String storeName, String location);
}
