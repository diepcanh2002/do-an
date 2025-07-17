package com.traicau.pitch.repository;

import com.traicau.pitch.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MatchRepository extends JpaRepository<Match,Long> {
    @Query(value = "select m.id ,start_time ,end_time,m.score_teama ,m.score_teamb ,m.status,\n" +
            "t.name as teamA,t.image_url as teamALogo,t2.name as teamB,t2.image_url as teamBLogo ,f.name as field \n" +
            "from matches m\n" +
            "inner join teams t on t.id =m.team_a_id\n" +
            "inner join teams t2 on t2.id =m.team_b_id\n" +
            "inner join fields f on f.id =m.field_id",nativeQuery = true)
    List<Map<String,Object>> getAll();

    @Query(value = "select \n" +
            "m.id ,DATE(m.start_time),TIME(m.start_time),TIME(m.end_time),f.name as field,m.status,\n" +
            "t.name as leftTeam,t.image_url as leftTeamImageUrl,m.score_teama as leftTeamScored,\n" +
            "t2.name as rightTeam,t2.image_url as rightTeamImageUrl,m.score_teamb as rightTeamScored\n" +
            "from matches m\n" +
            "inner join teams t on t.id =m.team_a_id \n" +
            "inner join teams t2 on t2.id =m.team_b_id\n" +
            "inner join fields f on f.id=m.field_id \n" +
            "where m.start_time  BETWEEN ?1 AND ?2",nativeQuery = true)
    List<Map<String,Object>> getMatchByRange(LocalDateTime startTime,LocalDateTime endTime);
}
