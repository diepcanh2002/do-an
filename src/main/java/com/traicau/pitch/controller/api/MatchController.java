package com.traicau.pitch.controller.api;

import com.traicau.pitch.model.Field;
import com.traicau.pitch.model.Match;
import com.traicau.pitch.model.MatchStatus;
import com.traicau.pitch.model.Team;
import com.traicau.pitch.repository.FieldRepository;
import com.traicau.pitch.repository.MatchRepository;
import com.traicau.pitch.repository.TeamRepository;
import com.traicau.pitch.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
@RestController
@RequestMapping("/api/match")
public class MatchController {

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    FieldRepository fieldRepository;

    @GetMapping("/get-all")
    CommonResponse<Object> getAll(){
        return  new CommonResponse<>(1000,"",matchRepository.getAll());
    }

    @GetMapping("/get-by-range")
    CommonResponse<Object> getByRange(
            @RequestParam(name = "startTime") LocalDateTime startTime,
            @RequestParam(name = "endTime") LocalDateTime endTime
    ){
        return  new CommonResponse<>(1000,"",matchRepository.getMatchByRange(startTime,endTime));
    }

    @PostMapping("/create")
    public CommonResponse<Object> createMatch(
            @RequestParam(name = "startTime") LocalDateTime startTime,
            @RequestParam(name = "endTime") LocalDateTime endTime,
            @RequestParam(name = "teamAId") Long teamAId,
            @RequestParam(name = "teamBId") Long teamBId,
            @RequestParam(name = "fieldId") Long fieldId
    ){
        Match match=new Match();
        match.setStartTime(startTime);
        match.setEndTime(endTime);

        Team teamA=teamRepository.findById(teamAId).get();
        match.setTeamA(teamA);
        Team teamB=teamRepository.findById(teamBId).get();
        match.setTeamB(teamB);
        Field field=fieldRepository.findById(fieldId).get();
        match.setField(field);
        match.setStatus(MatchStatus.NOT_STARTED);

        matchRepository.save(match);
        return new CommonResponse<>(1000,"Tạo trận đấu thành công",null);
    }

    @PostMapping("/update-scored")
    public CommonResponse<Object> updateScored(
            @RequestParam(name = "scoredTeamA") Integer scoredTeamA,
            @RequestParam(name = "scoredTeamB") Integer  scoredTeamB,
            @RequestParam(name = "id") Long id
    ){
        Match match=matchRepository.findById(id).get();
        match.setScoreTeamA(scoredTeamA);
        match.setScoreTeamB(scoredTeamB);
        match.setStatus(MatchStatus.IN_PROGRESS);
        matchRepository.save(match);
        return new CommonResponse<>(1000,"Cập nhật tỉ số thành công",null);
    }

    @PostMapping("/finish")
    public CommonResponse<Object> finish(
            @RequestParam(name = "id") Long id
    ){
        Match match=matchRepository.findById(id).get();
        match.setStatus(MatchStatus.FINISHED);
        matchRepository.save(match);
        return new CommonResponse<>(1000,"Trận đấu đã kết thúc",null);
    }

    @PostMapping("/cancel")
    public CommonResponse<Object> cancel(
            @RequestParam(name = "id") Long id
    ){
        Match match=matchRepository.findById(id).get();
        match.setStatus(MatchStatus.CANCELED);
        matchRepository.save(match);
        return new CommonResponse<>(1000,"Hủy trận thành công",null);
    }

}
