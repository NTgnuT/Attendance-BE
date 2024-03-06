package com.rikkei.managementuser.security.principal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class UserDetailImpl implements UserDetails {
    private String email;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities ;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    // Tài khoản chưa hết hạn?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Tài khoản không bị khóa?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Các thông tin xác thực chưa hết hạn?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Tài khoản này có được sử dụng?
    @Override
    public boolean isEnabled() {
        return true;
    }
}
