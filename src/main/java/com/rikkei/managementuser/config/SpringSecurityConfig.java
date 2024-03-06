package com.rikkei.managementuser.config;

import com.rikkei.managementuser.security.jwt.JwtAuthenticationEntryPoint;
import com.rikkei.managementuser.security.jwt.JwtAuthenticationFilter;
import com.rikkei.managementuser.security.principal.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig {
    @Bean
    public JwtAuthenticationEntryPoint jwtEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }
    @Autowired
    public JwtAuthenticationFilter JwtAuthenticationFilter;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder);
        dao.setUserDetailsService(userDetailService);
        return dao;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint())) // xử lí lỗi liên quan đến xác thực
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ko lưu trạng thái
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/user-management/auth/**").permitAll()
                                .requestMatchers("/user-management/admin/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers("/user-management/user/**").hasAnyAuthority("ROLE_USER")

                                // công khai với đường dẫn này
                                .anyRequest().authenticated() // các đường dẫn khác yêu cầu xác thực
                );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
