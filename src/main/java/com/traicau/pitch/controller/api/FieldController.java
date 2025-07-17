package com.traicau.pitch.controller.api;

import com.traicau.pitch.model.Field;
import com.traicau.pitch.model.PricingSlot;
import com.traicau.pitch.repository.FieldRepository;
import com.traicau.pitch.repository.PricingSlotRepository;
import com.traicau.pitch.response.CommonResponse;
import com.traicau.pitch.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/field")
public class FieldController {
    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    PricingSlotRepository pricingSlotRepository;

    @Autowired
    FieldService fieldService;

    @GetMapping("/get-all")
    CommonResponse<Object> getAll(){
        return  new CommonResponse<>(1000,"",fieldRepository.getAllField());
    }

    @GetMapping("/get-pricing-slots")
    CommonResponse<Object> getAll(@RequestParam(name = "date") LocalDate date,
                                  @RequestParam(name = "fieldId") Long fieldId
                                  ){
        return  new CommonResponse<>(1000,"",fieldRepository.getTimeSlotInField(fieldId,date));
    }

    @PostMapping("/create")
    public CommonResponse<Object> createField(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "openTime") LocalTime openTime,
            @RequestParam(name = "closeTime")LocalTime closeTime,
            @RequestParam(name = "image")MultipartFile image
            ){
       Field field=new Field();
       field.setType(type);
       field.setName(name);
       field.setAddress(address);
        try {
            String imageUrl = fieldService.saveFile(image);
            field.setImage(imageUrl);
        } catch (RuntimeException e) {
            new CommonResponse<>(1001,e.getMessage(),null);
        }
        field.setOpenTime(openTime);
        field.setCloseTime(closeTime);
       Field savingField=fieldRepository.save(field);
        List<PricingSlot> pricingSlots = new ArrayList<>();
        double price = 150000.0;
        for (int hour = openTime.getHour(); hour < closeTime.getHour(); hour++) {
            PricingSlot slot = new PricingSlot(savingField, LocalTime.of(hour, 0), LocalTime.of(hour + 1, 0), price);
            pricingSlots.add(slot);
        }
        pricingSlotRepository.saveAll(pricingSlots);
        return new CommonResponse<>(1000,"Tạo sân thành công",null);
    }

    @PostMapping("/update")
    public CommonResponse<Object> updateField(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "openTime") LocalTime openTime,
            @RequestParam(name = "closeTime") LocalTime closeTime,
            @RequestParam(name = "image",required = false)MultipartFile image
    ){
        Field field=fieldRepository.findById(id).get();
        field.setType(type);
        field.setName(name);
        field.setAddress(address);
        try {
            String imageUrl = fieldService.saveFile(image);
            field.setImage(imageUrl);
        } catch (RuntimeException e) {
            new CommonResponse<>(1001,e.getMessage(),null);
        }
        field.setOpenTime(openTime);
        field.setCloseTime(closeTime);
        fieldRepository.save(field);
        return new CommonResponse<>(1000,"Sửa sân thành công",null);
    }

    @GetMapping("/get-info-by-id")
    CommonResponse<Object> getPrice(
            @RequestParam("id")Long id
    ){
        return  new CommonResponse<>(1000,"",fieldRepository.getInfoById(id));
    }





}
