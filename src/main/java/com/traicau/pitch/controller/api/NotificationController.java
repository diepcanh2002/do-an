package com.traicau.pitch.controller.api;

import com.traicau.pitch.repository.NotificationRepository;
import com.traicau.pitch.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/get-all")
    CommonResponse<Object> getAll(
            @RequestParam Long userId){
        return  new CommonResponse<>(1000,"",notificationRepository.getNotificationByUserId(userId));
    }
}
