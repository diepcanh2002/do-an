package com.traicau.pitch.controller.api;

import com.traicau.pitch.model.*;
import com.traicau.pitch.repository.BookingRepository;
import com.traicau.pitch.repository.FieldRepository;
import com.traicau.pitch.repository.PaymentRepository;
import com.traicau.pitch.response.CommonResponse;
import com.traicau.pitch.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    FieldService fieldService;

    @GetMapping("/get-all")
    CommonResponse<Object> getAll(){
        return  new CommonResponse<>(1000,"",paymentRepository.getAllPayment());
    }

    //admin
    @GetMapping("/get-by-range")
    CommonResponse<Object> getPaymentByRange(
            @RequestParam(name = "startTime") LocalDate startTime,
            @RequestParam(name = "endTime") LocalDate endTime
    ){
        return  new CommonResponse<>(1000,"",paymentRepository.getByTimeRange(startTime,endTime));
    }


    @PostMapping("/confirm")
    public CommonResponse<Object> confirmPayment(@RequestParam(name = "paymentId") Long paymentId){
        Payment payment=paymentRepository.findById(paymentId).get();
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());
        //tạo thông báo
        Booking booking=bookingRepository.findById(payment.getBooking().getId()).get();
        Field field = fieldRepository.findById(booking.getField().getId()).get();
        String message = "Lịch đặt sân" + field.getName() + " ngày " + booking.getStartTime().toLocalDate()
                + " từ " + booking.getStartTime().toLocalTime() + "-" + booking.getEndTime().toLocalTime() +
                " đã được xác nhận thanh toán.Hẹn gặp lại lần sau!.";

        paymentRepository.save(payment);
        return new CommonResponse<>(1000,"Xác nhận thanh toán thành công",null);
    }

    @PostMapping("/cancel")
    public CommonResponse<Object> cancelPayment(@RequestParam(name = "paymentId") Long paymentId){
        Payment payment=paymentRepository.findById(paymentId).get();
        payment.setStatus(PaymentStatus.FAILED);

        //tạo thông báo
        Booking booking=bookingRepository.findById(payment.getBooking().getId()).get();
        Field field = fieldRepository.findById(booking.getField().getId()).get();
        String message = "Lịch đặt " + field.getName() + " ngày " + booking.getStartTime().toLocalDate()
                + " từ " + booking.getStartTime().toLocalTime() + "-" + booking.getEndTime().toLocalTime() +
                " đã bị hủy thanh toán.";
        fieldService.createNotification(message,NotificationType.DANGER,booking.getUser().getId());

        paymentRepository.save(payment);
        return new CommonResponse<>(1000,"Hủy thanh toán thành công",null);
    }


    @PostMapping("/changeMethodPayment")
    public CommonResponse<Object> changeMethodPayment(
            @RequestParam(name = "method") PaymentMethod method,
            @RequestParam(name = "paymentId") Long paymentId){
        Payment payment=paymentRepository.findById(paymentId).get();
        payment.setPaymentMethod(method);
        paymentRepository.save(payment);
        return new CommonResponse<>(1000,"Đổi phương thức thanh toán thành công",null);
    }


    @PostMapping("/refund")
    public CommonResponse<Object> refundedPayment(@RequestParam(name = "paymentId") Long paymentId){
        Payment payment=paymentRepository.findById(paymentId).get();
        payment.setStatus(PaymentStatus.FAILED);

        //tạo thông báo
        Booking booking=bookingRepository.findById(payment.getBooking().getId()).get();
        Field field = fieldRepository.findById(booking.getField().getId()).get();
        String message = "Lịch đặt " + field.getName() + " ngày " + booking.getStartTime().toLocalDate()
                + " từ " + booking.getStartTime().toLocalTime() + "-" + booking.getEndTime().toLocalTime() +
                " đã được hoàn tiền.";
        fieldService.createNotification(message,NotificationType.SUCCESS,booking.getUser().getId());

        paymentRepository.save(payment);
        return new CommonResponse<>(1000,"Thanh toán đã được hoàn tiền",null);
    }

    @GetMapping("/get-bill-by-booking-id")
    CommonResponse<Object> getAll(@RequestParam Long bookingId){
        return  new CommonResponse<>(1000,"",paymentRepository.getBillByBookingId(bookingId));
    }
}
