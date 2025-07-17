package com.traicau.pitch.repository;

import com.traicau.pitch.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Query(value = "SELECT *\n" +
            "FROM notifications n\n" +
            "WHERE n.user_id = ?1\n" +
            "ORDER BY n.sent_time DESC\n" +
            "LIMIT 25;",nativeQuery = true)
    List<Map<String,Object>> getNotificationByUserId(Long userId);
}
