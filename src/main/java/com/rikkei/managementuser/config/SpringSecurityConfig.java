package com.rikkei.managementuser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rikkei.managementuser.exception.CustomAccessDeniedHandler;
import com.rikkei.managementuser.model.dto.ErrorResponse;
import com.rikkei.managementuser.security.jwt.JwtAuthenticationEntryPoint;
import com.rikkei.managementuser.security.jwt.JwtAuthenticationFilter;
import com.rikkei.managementuser.security.principal.UserDetailServiceImpl;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
@EnableWebSecurity
@RequiredArgsConstructor
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
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

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

//        @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return (request, response, ex) -> {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//            ServletOutputStream out = response.getOutputStream();
//            new ObjectMapper().writeValue(out, new MyCustomErrorDTO());
//            out.flush();
//        };
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(auth -> auth.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:5173/", "http://localhost:5174/", "http://localhost:5175/"));
            configuration.setAllowedMethods(List.of("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setExposedHeaders(List.of("Authorization"));
            return configuration;
        }));
        http.csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint()))
                .exceptionHandling(ex->ex.accessDeniedHandler(customAccessDeniedHandler))

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ko lưu trạng thái
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/user-management/auth/**").permitAll()
                                .requestMatchers("/user-management/api/**").permitAll()
                                .requestMatchers("/user-management/api/courses/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/user-management/api/classes").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/user-management/api/courses/**").hasAnyAuthority("ROLE_ADMIN")
                                .anyRequest().authenticated() // các đường dẫn khác yêu cầu xác thực
                )
//                .exceptionHandling(ex -> ex.accessDeniedHandler((request, response, accessDeniedException) -> {
//                    throw  accessDeniedException;
//                }));
                ;
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
