package com.traicau.pitch.controller.api;

import com.traicau.pitch.repository.BookingRepository;
import com.traicau.pitch.repository.PaymentRepository;
import com.traicau.pitch.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping("/pie-chart")
    CommonResponse<Object> getPieChart(
            @RequestParam(name = "date") LocalDate date,
            @RequestParam(name = "mode") String mode
    ){
        return  new CommonResponse<>(1000,"",bookingRepository.getPieChart(date,mode));
    }
    @GetMapping("/column-chart")
    CommonResponse<Object> getColumnChart(
            @RequestParam(name = "date") LocalDate date,
            @RequestParam(name = "mode") String mode
    ){
        return  new CommonResponse<>(1000,"",bookingRepository.getColumChart(date,mode));
    }
    @GetMapping("/line-chart")
    CommonResponse<Object> getLineChart(
            @RequestParam(name = "date") LocalDate date,
            @RequestParam(name = "mode") String mode
    ){
        return  new CommonResponse<>(1000,"",paymentRepository.getLineChart(date,mode));
    }

}
