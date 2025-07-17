package com.traicau.pitch.repository;

import com.traicau.pitch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    boolean existsByPhone(String username);

    @Query(value = "select * from users",nativeQuery = true)
    List<Map<String,Object>> getAllUser();

    @Query(value = "select full_name,phone from users where id=?1",nativeQuery = true)
    Map<String,Object> getProfile(Long id);
}
