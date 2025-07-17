package com.traicau.pitch.controller.api;

import com.traicau.pitch.model.*;
import com.traicau.pitch.repository.*;
import com.traicau.pitch.response.CommonResponse;
import com.traicau.pitch.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    FieldService fieldService;

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;


    //admin
    @GetMapping("/get-booking-by-range")
    CommonResponse<Object> getBookingByRange(
            @RequestParam(name = "startTime") LocalDate startTime,
            @RequestParam(name = "endTime") LocalDate endTime
    ){
        return  new CommonResponse<>(1000,"",bookingRepository.getAllBookingByRange(startTime,endTime));
    }

    @PostMapping("/create")
    public CommonResponse<Object> createBooking(
            @RequestParam(name = "startTime") LocalDateTime startTime,
            @RequestParam(name = "endTime") LocalDateTime endTime,
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "pitchId") Long pitchId
    ) {
        if (fieldService.checkBookingValid(startTime, endTime, pitchId)) {
            Field field = fieldRepository.findById(pitchId).get();
            if(startTime.toLocalTime().isBefore(field.getOpenTime()) ||
            endTime.toLocalTime().isAfter(field.getCloseTime())
            ){
                return new CommonResponse<>(1002, "Thời gian lịch đặt nằm ngoài khung giờ hoạt động", null);
            }

            User user = userRepository.findById(userId).get();
            Booking booking = new Booking();
            booking.setStatus(BookingStatus.PENDING);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);
            booking.setUser(user);
            booking.setField(field);
            bookingRepository.save(booking);

            //tao thong bao
            String message = "Lịch đặt " + field.getName() + " " + startTime.toLocalDate()
                    + " " + startTime.toLocalTime() + "-" + endTime.toLocalTime() +
                    " đặt thành công. Đang chờ chủ sân bóng xác nhận";
            fieldService.createNotification(message, NotificationType.INFO, userId);

            return new CommonResponse<>(1000, "Đặt sân thành công", null);

        } else {
            return new CommonResponse<>(1001, "Lịch đặt bị trùng lịch", null);
        }

    }

    @PostMapping("/confirm")
    public CommonResponse<Object> confirmBooking(
            @RequestParam(name = "bookingId") Long bookingId
    ) {
        Booking booking = bookingRepository.findById(bookingId).get();
        if (fieldService.checkBookingValid(booking.getStartTime(), booking.getEndTime(), booking.getField().getId())) {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
            //tao thanh toan
            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setPaymentMethod(PaymentMethod.CASH);
            payment.setStatus(PaymentStatus.PENDING);
            Double total = fieldService.calculatePrice(booking.getField(), booking.getStartTime().toLocalTime(), booking.getEndTime().toLocalTime());
            payment.setTotal(total);
            paymentRepository.save(payment);
            //tao thong bao
            Field field = fieldRepository.findById(booking.getField().getId()).get();
            String message = "Lịch đặt " + field.getName() + " ngày " + booking.getStartTime().toLocalDate()
                    + " từ " + booking.getStartTime().toLocalTime() + "-" + booking.getEndTime().toLocalTime() +
                    " đã được xác nhận.Sân đã sẵn sàng!.";
            fieldService.createNotification(message, NotificationType.SUCCESS, booking.getUser().getId());

            return new CommonResponse<>(1000, "Xác nhận thành công", null);
        } else {
            return new CommonResponse<>(1001, "Lịch đặt bị trùng lịch", null);
        }
    }


    @PostMapping("/cancel")
    public CommonResponse<Object> cancelBooking(
            @RequestParam(name = "bookingId") Long bookingId
    ) {
        String message;
        Booking booking = bookingRepository.findById(bookingId).get();
        Field field = fieldRepository.findById(booking.getField().getId()).get();
        //Huy thanh toan neu o trang thai confim
        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            Payment payment = paymentRepository.findById(booking.getPayment().getId()).get();
            if(payment.getStatus()==PaymentStatus.PAID){
                payment.setStatus(PaymentStatus.REFUNDED);
                message = "Lịch đặt " + field.getName() + " ngày " + booking.getStartTime().toLocalDate()
                        + " từ " + booking.getStartTime().toLocalTime() + "-" + booking.getEndTime().toLocalTime() +
                        " đã bị hủy.Chúng tôi chân thành xin lỗi vì sự bất tiện này.Vui lòng liên hệ chủ sân để nhận lại tiền đặt.";
                fieldService.createNotification(message, NotificationType.DANGER, booking.getUser().getId());
            }else {
                payment.setStatus(PaymentStatus.FAILED);
                message = "Lịch đặt " + field.getName() + " ngày " + booking.getStartTime().toLocalDate()
                        + " từ " + booking.getStartTime().toLocalTime() + "-" + booking.getEndTime().toLocalTime() +
                        " đã bị hủy.Chúng tôi chân thành xin lỗi vì sự bất tiện này.";
                fieldService.createNotification(message, NotificationType.DANGER, booking.getUser().getId());
            }

        }else{
            message = "Lịch đặt " + field.getName() + " ngày " + booking.getStartTime().toLocalDate()
                    + " từ " + booking.getStartTime().toLocalTime() + "-" + booking.getEndTime().toLocalTime() +
                    " đã bị hủy.Chúng tôi chân thành xin lỗi vì sự bất tiện này.";
            fieldService.createNotification(message, NotificationType.DANGER, booking.getUser().getId());
        }
        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
        //tao thong bao


        return new CommonResponse<>(1000, "Hủy lịch thành công", null);
    }

    @GetMapping("/get-booking-by-range-and-user-id")
    CommonResponse<Object> getBookingByRangeByUserId(
            @RequestParam(name = "startTime") LocalDateTime startTime,
            @RequestParam(name = "endTime") LocalDateTime endTime,
            @RequestParam(name = "userId") Long userId
    ){
        return  new CommonResponse<>(1000,"call ok"
                ,bookingRepository.getBookingByRangeByUserId(startTime,endTime,userId));
    }


    @PostMapping("/cancel-booking-for-user")
    public CommonResponse<Object> cancelBookingForUser(
            @RequestParam(name = "bookingId") Long bookingId
    ) {
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
        return new CommonResponse<>(1000, "Hủy lịch thành công", null);
    }


    //for admin
    @PostMapping("/add")
    public CommonResponse<Object> addBooking(
            @RequestParam(name = "startTime") LocalDateTime startTime,
            @RequestParam(name = "endTime") LocalDateTime endTime,
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "pitchId") Long pitchId
    ) {
        if (fieldService.checkBookingValid(startTime, endTime, pitchId)) {
            Field field = fieldRepository.findById(pitchId).get();
            if(startTime.toLocalTime().isBefore(field.getOpenTime()) ||
                    endTime.toLocalTime().isAfter(field.getCloseTime())
            ){
                return new CommonResponse<>(1002, "Thời gian lịch đặt nằm ngoài khung giờ hoạt động", null);
            }
            User user = userRepository.findById(userId).get();
            Booking booking = new Booking();
            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);
            booking.setUser(user);
            booking.setField(field);
            Booking savedBooking=bookingRepository.save(booking);

            Payment payment = new Payment();
            payment.setBooking(savedBooking);
            payment.setPaymentMethod(PaymentMethod.CASH);
            payment.setStatus(PaymentStatus.PENDING);
            Double total = fieldService.calculatePrice(savedBooking.getField(), savedBooking.getStartTime().toLocalTime(), savedBooking.getEndTime().toLocalTime());
            payment.setTotal(total);
            paymentRepository.save(payment);

            return new CommonResponse<>(1000, "Đặt sân thành công", null);

        } else {
            return new CommonResponse<>(1001, "Lịch đặt bị trùng lịch", null);
        }

    }



}
