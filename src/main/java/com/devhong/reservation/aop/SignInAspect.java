package com.devhong.reservation.aop;

import com.devhong.reservation.dto.Auth;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SignInAspect {

    @AfterReturning("execution(* com.devhong.reservation.controller.AuthController.signIn(..))")
    public void AfterSignIn(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Auth.SignIn user = (Auth.SignIn) args[0];

        log.info(user.getUsername() + " SignIn Success");
    }

    @Before(value = "@annotation(BeforeSignIn)")
    public void beforeSignIn(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Auth.SignIn user = (Auth.SignIn) args[0];

        log.info(user.getUsername() + " try SignIn");
    }

}
