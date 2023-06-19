package com.devhong.reservation.service;

import com.devhong.reservation.dto.Auth;
import com.devhong.reservation.exception.CustomErrorCode;
import com.devhong.reservation.exception.CustomException;
import com.devhong.reservation.model.User;
import com.devhong.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(Auth.SignUp user) {
        if (userRepository.existsByUsername(user.getUsername())){
            throw new CustomException(CustomErrorCode.USER_ALREADY_EXISTS);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user.toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
