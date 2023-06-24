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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
        회원 등록
        1. client가 요청보낸 회원가입 정보를 가져와서 이미 가입된 아이디인지 확인
        2. 이미 있는 아이디면 예외발생
        3. 없으면 비밀번호는 암호화 처리 후 setPassword
        4. SignUpDto 객체를 UserEntity객체로 변환 후 DB에 저장(회원가입 완료)
     */
    public User register(Auth.SignUp user) {
        if (userRepository.existsByUsername(user.getUsername())){
            throw new CustomException(CustomErrorCode.USER_ALREADY_EXISTS);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user.toEntity());
    }

    /*
        로그인
        1. client에서 보내온 로그인 요청을 가져와서 아이디가 존재하는지, 비밀번호는 일치하는지 확인
        2. 아이디있고 비밀번호 일치하면 user엔티티 객체 리턴
     */
    public User authenticate(Auth.SignIn user){
        User userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);
        }

        return userEntity;
    }

    /*
        jwt 토큰 바디에 포함되어있는 username으로 UserDetails 객체 가져오는 메서드.
        클라이언트가 요청시 JwtAuthenticaionFilter에서 토큰 검증과, 권한확인을 하는 과정에서 쓰임.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));
    }
}
