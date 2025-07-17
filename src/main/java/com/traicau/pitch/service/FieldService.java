package com.traicau.pitch.service;

import com.traicau.pitch.model.*;
import com.traicau.pitch.repository.BookingRepository;
import com.traicau.pitch.repository.NotificationRepository;
import com.traicau.pitch.repository.PricingSlotRepository;
import com.traicau.pitch.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FieldService {
    @Autowired
    PricingSlotRepository pricingSlotRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;


    @Value("${image.url}")
    private String source;

    @Value("${path.image.folder}")
    private String folderDirection;

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        return Arrays.asList("png", "jpg", "jpeg", "bmp").contains(fileExtension.trim());
    }
    public String saveFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Ảnh rỗng");
            }
            if (!isImageFile(file)) {
                throw new RuntimeException("Không phải ảnh");
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            // Tạo tên tệp ngẫu nhiên
            String generatedFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtension;
            // Xử lý đường dẫn theo hệ điều hành
            Path destinationFolderPath = Paths.get(folderDirection).normalize().toAbsolutePath();
            // Tạo đường dẫn đầy đủ cho tệp
            Path destinationFilePath = destinationFolderPath.resolve(generatedFileName);
            if (!destinationFilePath.getParent().equals(destinationFolderPath)) {
                throw new RuntimeException("Không thể lưu ngoài đường dẫn");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.createDirectories(destinationFolderPath);  // Đảm bảo thư mục tồn tại
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return source+generatedFileName;  // Trả về tên tệp đã lưu
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu. thử lại", e);
        }
    }



    public boolean checkBookingValid(LocalDateTime startTime, LocalDateTime endTime, Long pitchId) {
        List<Booking> list = bookingRepository.getBookingByDayAndPitchId(startTime.toLocalDate(), pitchId);
        if (list.isEmpty()) {
            return true;
        }
        if (endTime.isBefore(list.getFirst().getStartTime()) || endTime.isEqual(list.getFirst().getStartTime())) {
            return true;
        }
        if (startTime.isAfter(list.getLast().getEndTime()) || startTime.isEqual(list.getLast().getEndTime())) {
            return true;
        }
        for (Booking booking : list) {
            if (startTime.isEqual(booking.getStartTime()) || endTime.isEqual(booking.getEndTime())) {
                return false;
            }
        }
        for (Booking booking : list) {
            if (startTime.isBefore(booking.getStartTime()) && endTime.isAfter(booking.getStartTime())) {
                return false;
            }
        }
        for (Booking booking : list) {
            if (startTime.isAfter(booking.getStartTime()) && startTime.isBefore(booking.getEndTime())) {
                return false;
            }
        }
        for (int i = 0; i < list.size() - 1; i++) {
            if ((startTime.isAfter(list.get(i).getEndTime()) || startTime.isEqual(list.get(i).getEndTime()))
                    && (endTime.isBefore(list.get(i + 1).getStartTime()) || endTime.isEqual(list.get(i + 1).getStartTime()))) {
                return true;
            }
        }
        return false;
    }


    public double calculatePrice(Field field, LocalTime startTime, LocalTime endTime) {
        List<PricingSlot> pricingSlots = pricingSlotRepository.findByField(field);
        double totalCost = 0.0;

        for (PricingSlot slot : pricingSlots) {
            // Nếu toàn bộ khoảng thời gian trùng với một slot, lấy luôn giá slot
            if (startTime.equals(slot.getStartTime()) && endTime.equals(slot.getEndTime())) {
                return slot.getPrice();
            }

            // Nếu có giao với slot hiện tại
            if (!(endTime.isBefore(slot.getStartTime()) || startTime.isAfter(slot.getEndTime()))) {
                LocalTime effectiveStart = startTime.isBefore(slot.getStartTime()) ? slot.getStartTime() : startTime;
                LocalTime effectiveEnd = endTime.isAfter(slot.getEndTime()) ? slot.getEndTime() : endTime;

                // Tính giá cho phần trong khoảng slot
                long minutesInSlot = Duration.between(effectiveStart, effectiveEnd).toMinutes();
                double priceForSlot = (minutesInSlot / 60.0) * slot.getPrice();
                totalCost += priceForSlot;
            }
        }
        return totalCost;
    }

    public void createNotification(String message, NotificationType type,Long userId){
        User user=userRepository.findById(userId).get();
        Notification notification=new Notification();
        notification.setUser(user);
        notification.setType(type);
        notification.setSentTime(LocalDateTime.now());
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

}
