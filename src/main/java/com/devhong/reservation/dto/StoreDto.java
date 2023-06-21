package com.devhong.reservation.dto;

import com.devhong.reservation.model.Reservation;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StoreDto {

    @Data
    public static class Registration{
        @NotNull
        private Long userId;

        @NotBlank
        private String storeName;

        @NotBlank
        private String location;

        @NotBlank
        private String description;

        public Store toEntity(User user) {
            return Store.builder()
                    .user(user)
                    .storeName(storeName)
                    .location(location)
                    .description(description)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RegistrationResponse{
        private String storeName;
        private String location;

        public static RegistrationResponse fromEntity(Store store) {
            return RegistrationResponse.builder()
                    .storeName(store.getStoreName())
                    .location(store.getLocation())
                    .build();
        }
    }

    @Data
    public static class Reserve{
        @NotNull
        private Long userId;

        @NotNull
        private Long storeId;

        @NotBlank
        private String reservationTime;

        public Reservation toEntity(User user, Store store) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return Reservation.builder()
                    .user(user)
                    .store(store)
                    .reservationTime(LocalDateTime.parse(reservationTime,formatter))
                    .reservedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReservationResponse{
        private String userName;
        private String storeName;
        private String location;
        private LocalDateTime reservationTime;
        private LocalDateTime reservedAt;

        public static ReservationResponse fromEntity(Reservation reservation) {
            return ReservationResponse.builder()
                    .userName(reservation.getUser().getUsername())
                    .storeName(reservation.getStore().getStoreName())
                    .location(reservation.getStore().getLocation())
                    .reservationTime(reservation.getReservationTime())
                    .reservedAt(reservation.getReservedAt())
                    .build();
        }
    }
}
