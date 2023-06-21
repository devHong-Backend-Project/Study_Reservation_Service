package com.devhong.reservation.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Store store;

    private LocalDateTime reservationTime;

    private LocalDateTime reservedAt;
    private LocalDateTime canceledAt;

    private boolean isConfirmed = false;

    private boolean isVisited = false;

    private boolean isCanceled = false;

}
