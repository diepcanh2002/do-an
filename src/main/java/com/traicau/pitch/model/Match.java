package com.traicau.pitch.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field; // Liên kết sân nơi diễn ra trận đấu

    @ManyToOne
    @JoinColumn(name = "team_a_id", nullable = false)
    private Team teamA; // Đội A

    @ManyToOne
    @JoinColumn(name = "team_b_id", nullable = false)
    private Team teamB; // Đội B

    @Column(nullable = false)
    private Integer scoreTeamA = 0;

    @Column(nullable = false)
    private Integer scoreTeamB = 0;

    @Column(nullable = false)
    private LocalDateTime startTime; // Thời gian bắt đầu trận đấu

    @Column(nullable = false)
    private LocalDateTime endTime; // Thời gian kết thúc trận đấu

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status; // Trạng thái trận đấu

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public Integer getScoreTeamA() {
        return scoreTeamA;
    }

    public void setScoreTeamA(Integer scoreTeamA) {
        this.scoreTeamA = scoreTeamA;
    }

    public Integer getScoreTeamB() {
        return scoreTeamB;
    }

    public void setScoreTeamB(Integer scoreTeamB) {
        this.scoreTeamB = scoreTeamB;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }
}
