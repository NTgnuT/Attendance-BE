package com.rikkei.managementuser.security.principal;

import com.rikkei.managementuser.model.entity.User;
import com.rikkei.managementuser.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
        List<GrantedAuthority> list = user.getRoles().stream().map(r ->
                new SimpleGrantedAuthority(r.getRole().name())).collect(Collectors.toList());
        return UserDetailImpl.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .grantedAuthorities(list)
                .build();
    }
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        List<GrantedAuthority> list = user.getRoles().stream().map(r ->
                new SimpleGrantedAuthority(r.getRole().name())).collect(Collectors.toList());
        return UserDetailImpl.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .grantedAuthorities(list)
                .build();
    }
}
