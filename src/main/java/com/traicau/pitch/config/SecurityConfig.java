package com.traicau.pitch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.ArrayList;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Không mã hóa mật khẩu
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,CustomAuthenticationSuccessHandler successHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/**").permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Đảm bảo session tồn tại
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=true") // Chuyển hướng đến trang login với tham số error
                        //lưu cookie
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Đường dẫn logout
                        .logoutSuccessUrl("/")  // Chuyển hướng sau khi đăng xuất thành công
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(accessDeniedHandler())
                )
                .userDetailsService(userDetailsService); // Sử dụng UserDetailsService tùy chỉnh


        return http.build();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/401");
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if ("admin".equals(username)) {
                return new User("admin", "{noop}admin123", new ArrayList<>());
            } else if ("user".equals(username)) {
                return new User("user", "{noop}user123", new ArrayList<>());
            } else {
                throw new UsernameNotFoundException("User not found!");
            }
        };
    }

}
