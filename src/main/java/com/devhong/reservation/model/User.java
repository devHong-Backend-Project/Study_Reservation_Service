package com.devhong.reservation.model;

import com.devhong.reservation.type.UserType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String email;

    private String mobileNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
