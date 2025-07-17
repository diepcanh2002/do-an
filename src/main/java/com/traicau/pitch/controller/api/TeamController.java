package com.traicau.pitch.controller.api;

import com.traicau.pitch.model.Team;
import com.traicau.pitch.repository.TeamRepository;
import com.traicau.pitch.response.CommonResponse;
import com.traicau.pitch.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    FieldService fieldService;

    @GetMapping("/get-all")
    CommonResponse<Object> getAll(){
        return  new CommonResponse<>(1000,"",teamRepository.getAllTeam());
    }

    @PostMapping("/create")
    public CommonResponse<Object> createTeam(
            @RequestParam(name = "name") String name,
             @RequestParam(name = "image") MultipartFile image
    ){
        Team team=new Team();
        team.setName(name);
        try {
            String imageUrl = fieldService.saveFile(image);
            team.setImageUrl(imageUrl);
        } catch (RuntimeException e) {
            new CommonResponse<>(1001,e.getMessage(),null);
        }
        teamRepository.save(team);
        return new CommonResponse<>(1000,"Thêm đội bóng thành công",null);
    }



    @PostMapping("/update")
    public CommonResponse<Object> updateTeam(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "image",required = false) MultipartFile image,
            @RequestParam(name = "id") Long id
    ){
        Team team=teamRepository.findById(id).get();
        team.setName(name);
        if(image!=null){
            try {
                String imageUrl = fieldService.saveFile(image);
                team.setImageUrl(imageUrl);
            } catch (RuntimeException e) {
                new CommonResponse<>(1001,e.getMessage(),null);
            }
        }
        teamRepository.save(team);
        return new CommonResponse<>(1000,"Sửa đội bóng thành công",null);
    }

}
