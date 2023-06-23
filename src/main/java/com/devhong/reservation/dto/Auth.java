package com.devhong.reservation.dto;

import com.devhong.reservation.model.User;
import com.devhong.reservation.type.UserType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class Auth {

    /*
        로그인시 클라이언트 request Dto
     */
    @Data
    public static class SignIn{
        @NotBlank
        private String username;

        @NotBlank
        private String password;
    }

    /*
        회원가입시 클라이언트 request Dto
     */
    @Data
    public static class SignUp{
        @NotBlank
        private String username;

        @NotBlank
        @Length(min = 5)
        private String password;

        @NotBlank
        private String email;

        @NotBlank
        private String userType;

        @NotBlank
        private String mobileNumber;

        @NotEmpty
        private List<String> roles;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .userType(UserType.valueOf(userType))
                    .mobileNumber(mobileNumber)
                    .roles(roles)
                    .build();
        }
    }

    /*
        회원가입 성공시 서버 응답 Dto
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SignUpResponse{
        private String username;
        private String email;
        private String mobileNumber;

        public static SignUpResponse fromEntity(User user) {
            return SignUpResponse.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .mobileNumber(user.getMobileNumber())
                    .build();
        }
    }
}
