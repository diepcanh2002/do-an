package com.traicau.pitch.service;

import com.traicau.pitch.model.User;
import com.traicau.pitch.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Truy vấn người dùng từ cơ sở dữ liệu
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        // Tạo đối tượng UserDetails từ cơ sở dữ liệu
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Mật khẩu đã mã hóa trong cơ sở dữ liệu
                .roles(String.valueOf(user.getRole()))// Vai trò của người dùng
                .build();
    }
}