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

    /*
        1. client가 요청보낸 회원가입 정보를 가져와서 이미 가입된 아이디인지 확인
        2. 이미 있는 아이디면 에러발생
        3. 없으면 비밀번호는 암호화 처리해서 회원가입 계속 진행
        4. request 객체를 Entity객체로 변환 후 DB에 저장(회원가입 완료)
     */
    public User register(Auth.SignUp request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new CustomException(CustomErrorCode.USER_ALREADY_EXISTS);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(request.toEntity());
    }

    /*
        1. client에서 보내온 로그인 요청을 가져와서 아이디가 존재하는지, 비밀번호는 일치하는지 확인
        2. 각 상황에 대해 에러 처리
        3. 아이디있고 비밀번호 일치하면 user엔티티 객체 리턴
     */
    public User authenticate(Auth.SignIn request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));
    }
}
