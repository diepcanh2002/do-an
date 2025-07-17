package com.traicau.pitch.repository;

import com.traicau.pitch.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TeamRepository extends JpaRepository<Team,Long> {
    @Query(value = "select *from teams",nativeQuery = true)
    List<Map<String,Object>> getAllTeam();
}
