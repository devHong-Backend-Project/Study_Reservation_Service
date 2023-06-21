package com.devhong.reservation.repository;

import com.devhong.reservation.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByStoreNameContainingIgnoreCase(String storeName);

    boolean existsByStoreNameAndLocation(String storeName, String location);
}
