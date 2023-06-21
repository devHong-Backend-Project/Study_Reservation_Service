package com.devhong.reservation.dto;

import com.devhong.reservation.model.Reservation;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationDto {

    @Data
    public static class Reserve{
        @NotBlank
        private String userName;

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
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReservationResponse{
        private String userName;
        private String mobileNumber;
        private String storeName;
        private String location;
        private LocalDateTime reservationTime;

        public static ReservationResponse fromEntity(Reservation reservation) {
            return ReservationResponse.builder()
                    .userName(reservation.getUser().getUsername())
                    .mobileNumber(reservation.getUser().getMobileNumber())
                    .storeName(reservation.getStore().getStoreName())
                    .location(reservation.getStore().getLocation())
                    .reservationTime(reservation.getReservationTime())
                    .build();
        }
    }

    @Data
    public static class Confirm{
        private Long reservationId;
    }

    @Data
    @Builder
    public static class ConfirmResponse{
        private String userName;
        private String storeName;
        private LocalDateTime reservationTime;
        private boolean isConfirmed;
        private LocalDateTime confirmedAt;

        public static ConfirmResponse fromEntity(Reservation reservation) {
            return ConfirmResponse.builder()
                    .userName(reservation.getUser().getUsername())
                    .storeName(reservation.getStore().getStoreName())
                    .reservationTime(reservation.getReservationTime())
                    .isConfirmed(reservation.isConfirmed())
                    .confirmedAt(reservation.getReservedAt())
                    .build();
        }
    }

    @Data
    @Builder
    public static class CancelResponse{
        private String userName;
        private String storeName;
        private LocalDateTime reservationTime;
        private boolean isCanceled;
        private LocalDateTime canceledAt;

        public static CancelResponse fromEntity(Reservation reservation) {
            return CancelResponse.builder()
                    .userName(reservation.getUser().getUsername())
                    .storeName(reservation.getStore().getStoreName())
                    .reservationTime(reservation.getReservationTime())
                    .isCanceled(reservation.isCanceled())
                    .canceledAt(reservation.getCanceledAt())
                    .build();
        }
    }
}
