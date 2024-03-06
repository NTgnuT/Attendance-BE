package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.SignInFailException;
import com.rikkei.managementuser.model.dto.SignIn;
import com.rikkei.managementuser.model.entity.User;
import com.rikkei.managementuser.repository.IUserRepository;
import com.rikkei.managementuser.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-management/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@Valid @RequestBody SignIn signIn) throws SignInFailException {
        return new ResponseEntity<>(authenticationService.signIn(signIn), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> singUp(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user);

    }

    @GetMapping("/log-out")
    public  ResponseEntity<?> logOut(HttpServletRequest request , HttpServletResponse response){
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request,response,null);
        securityContextLogoutHandler.logout(request,response,null);
        return ResponseEntity.status(204).body(null);

    }

}
