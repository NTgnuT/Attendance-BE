package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.model.dto.request.UserSignUp;
import com.rikkei.managementuser.model.entity.User;
import com.rikkei.managementuser.repository.IUserRepository;
import com.rikkei.managementuser.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(UserSignUp userSignUp) {
        userRepository.save(User.builder()
                .username(userSignUp.getUsername())
                .password(passwordEncoder.encode(userSignUp.getPassword()))
                .email(userSignUp.getEmail())
                .fullName(userSignUp.getFullName())
                .status(1)
                .avatar(userSignUp.getAvatar())
                .phone(userSignUp.getPhone())
                .created_at(new Date())
                .update_at(new Date())
                .build());
    }

    public static void main(String[] args) {
        String plainPassword = "123456";

        // Mã hóa mật khẩu
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        // In ra mật khẩu đã mã hóa
        System.out.println("Mật khẩu đã mã hóa: " + hashedPassword);
    }
}
