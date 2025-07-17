package com.traicau.pitch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traicau.pitch.model.Role;
import com.traicau.pitch.model.User;
import com.traicau.pitch.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Lấy username từ đối tượng Authentication
        String username = authentication.getName();
        User user=userRepository.findByUsername(username);


        // Redirect sau khi đăng nhập thành công
//        if(user.getRole()== Role.USER){
//            response.sendRedirect("/");
//        }else {
//            response.sendRedirect("/admin/");
//        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("role", user.getRole().toString());

        response.getWriter().write(new ObjectMapper().writeValueAsString(data));
        response.getWriter().flush();

    }
}