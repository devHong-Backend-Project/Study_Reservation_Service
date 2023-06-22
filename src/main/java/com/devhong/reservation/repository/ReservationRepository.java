package com.devhong.reservation.repository;

import com.devhong.reservation.model.Reservation;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByUserIdAndStoreIdAndReservationTime(Long userId, Long storeId, LocalDateTime reservationTime);

    int countByStoreIdAndIsCanceledAndReservationTime(Long storeId, boolean canceled, LocalDateTime reservationTime);

    boolean existsByUserAndStoreAndIsVisited(User user, Store store, boolean isVisited);
}
