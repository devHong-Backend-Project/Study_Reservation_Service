package com.devhong.reservation.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Auth {

    @Data
    public static class SignIn{
        private String username;
        private String password;
    }

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
    }
}
