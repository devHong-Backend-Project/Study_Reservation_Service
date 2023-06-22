package com.devhong.reservation.repository;

import com.devhong.reservation.model.Reservation;
import com.devhong.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByUserIdAndStoreIdAndReservationTime(Long userId, Long storeId, LocalDateTime reservationTime);

    int countByStoreIdAndIsCanceledAndReservationTime(Long storeId, boolean canceled, LocalDateTime reservationTime);

    List<Reservation> findByStoreIdAndUser(Long reservationId, User user);
}
