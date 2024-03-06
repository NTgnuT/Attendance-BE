package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.SignInFailException;
import com.rikkei.managementuser.model.dto.SignIn;
import com.rikkei.managementuser.model.entity.User;
import com.rikkei.managementuser.repository.IUserRepository;
import com.rikkei.managementuser.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String signIn(SignIn signIn) throws SignInFailException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new SignInFailException("Invalid email or password.");
        }

        User user = userRepository.findByEmail(signIn.getEmail()).orElseThrow(()->new UsernameNotFoundException("Username notfound"));

        return jwtTokenProvider.generateTokenEmail(user);
    }


}
