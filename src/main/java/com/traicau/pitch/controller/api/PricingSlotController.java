package com.traicau.pitch.controller.api;

import com.traicau.pitch.model.Field;
import com.traicau.pitch.model.PricingSlot;
import com.traicau.pitch.repository.FieldRepository;
import com.traicau.pitch.repository.PricingSlotRepository;
import com.traicau.pitch.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/pricing-slot")
public class PricingSlotController {
    @Autowired
    PricingSlotRepository pricingSlotRepository;

    @Autowired
    FieldRepository fieldRepository;

    @GetMapping("/get-by-field-id")
    CommonResponse<Object> getAll(
            @RequestParam Long id
    ){
        return  new CommonResponse<>(1000,"",pricingSlotRepository.getByFieldId(id));
    }

    @PostMapping("/create")
    public CommonResponse<Object> updateTimeSlot(
            @RequestParam(name = "startTime") LocalTime startTime,
            @RequestParam(name = "endTime") LocalTime endTime,
            @RequestParam(name = "fieldId") Long fieldId,
            @RequestParam(name = "price") Double price
    ){
        Field field=fieldRepository.findById(fieldId).get();
        PricingSlot pricingSlot=new PricingSlot();
        pricingSlot.setStartTime(startTime);
        pricingSlot.setEndTime(endTime);
        pricingSlot.setPrice(price);
        pricingSlot.setField(field);
        pricingSlotRepository.save(pricingSlot);
        return new CommonResponse<>(1000,"Thêm khung giờ thành công",null);
    }

    @DeleteMapping("/delete")
    public CommonResponse<Object> deletePricingSlot(@RequestParam(name = "id") Long id
    ){
        pricingSlotRepository.deleteById(id);
        return new CommonResponse<>(1000,"Xóa khung giờ thành công",null);
    }


    @PostMapping("/update")
    public CommonResponse<Object> updateField(
            @RequestParam(name = "startTime") LocalTime startTime,
            @RequestParam(name = "endTime") LocalTime endTime,
            @RequestParam(name = "price") Double price,
            @RequestParam(name = "id") Long id
    ){
        PricingSlot pricingSlot=pricingSlotRepository.findById(id).get();
        pricingSlot.setStartTime(startTime);
        pricingSlot.setEndTime(endTime);
        pricingSlot.setPrice(price);
        pricingSlotRepository.save(pricingSlot);
        return new CommonResponse<>(1000,"Sửa thành công",null);
    }


    @GetMapping("/get-by-id")
    CommonResponse<Object> getById(
            @RequestParam Long id
    ){
        return  new CommonResponse<>(1000,"",pricingSlotRepository.getPricingSlotById(id));
    }
}
