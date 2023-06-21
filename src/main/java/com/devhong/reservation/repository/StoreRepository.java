package com.devhong.reservation.repository;

import com.devhong.reservation.model.Reservation;
import com.devhong.reservation.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByStoreNameAndLocation(String storeName, String location);

    boolean existsByStoreNameAndLocation(String storeName, String location);
}
