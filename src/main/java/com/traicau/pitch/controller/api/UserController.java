package com.traicau.pitch.controller.api;

import com.traicau.pitch.model.Role;
import com.traicau.pitch.model.User;
import com.traicau.pitch.repository.UserRepository;
import com.traicau.pitch.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/get-all")
    CommonResponse<?> getAllUser() {
        return new CommonResponse<>(1000, "", userRepository.getAllUser());
    }

    @GetMapping("/get-profile")
    CommonResponse<?> getUserById(
            @RequestParam Long userId
    ) {
        return new CommonResponse<>(1000, "", userRepository.getProfile(userId));
    }

    @PostMapping("/update")
    CommonResponse<Object> updateUser(
            @RequestParam Long userId,
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam(name = "oldPassword", required = false) String oldPassword,
            @RequestParam(name = "newPassword", required = false) String newPassword
    ) {
        User user = userRepository.findById(userId).get();
        if (userRepository.existsByPhone(phone)) {
            return new CommonResponse<>(1001, "Số điện thoại đã tồn tại", null);
        }
        if (oldPassword != null) {
            if (!oldPassword.equals(user.getPassword())) {
                return new CommonResponse<>(1001, "Mật khẩu cũ không đúng", null);
            }
            user.setFullName(fullName);
            user.setPhone(phone);
            user.setPassword(newPassword);
            userRepository.save(user);
            return new CommonResponse<>(1000, "Sửa thành công", null);
        } else {
            user.setFullName(fullName);
            user.setPhone(phone);
            userRepository.save(user);
            return new CommonResponse<>(1000, "Sửa thành công", null);
        }
    }

    @PostMapping("/register")
    public CommonResponse register(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("fullName") String fullName,
                                   @RequestParam("phone") String phone) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new CommonResponse<>(1001, "Tên tài khoản đã tồn tại", null);
        }

        if (userRepository.existsByPhone(phone)) {
            return new CommonResponse<>(1001, "Số điện thoại đã tồn tại", null);
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPhone(phone);
        newUser.setRole(Role.USER);
        newUser.setPassword(password);
        newUser.setFullName(fullName);
        userRepository.save(newUser);
        return new CommonResponse<>(1000, "Tài khoản tạo thành công!", null);
    }

    @PostMapping("/update-info")
    CommonResponse<Object> update(
            @RequestParam Long id,
            @RequestParam Role role
    ) {

        User user = userRepository.findById(id).get();
        user.setRole(role);
        userRepository.save(user);
        return new CommonResponse<>(1000, "Phân quyền thành công", null);

    }


}
