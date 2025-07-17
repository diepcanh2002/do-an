package com.traicau.pitch.repository;

import com.traicau.pitch.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FieldRepository extends JpaRepository<Field,Long> {
    @Query(value = "select *from fields",nativeQuery = true)
    List<Map<String,Object>> getAllField();


    @Query(value = "SELECT \n" +
            "    ps.id AS slotId, \n" +
            "    ps.start_time AS slotStartTime, \n" +
            "    ps.end_time AS slotEndTime, \n" +
            "    ps.price AS price,\n" +
            "    CASE WHEN COALESCE(b.status, 'NONE') = 'CONFIRMED' THEN 'Đã đặt' ELSE 'Trống' END AS bookingStatus\n" +
            "FROM pricing_slots ps\n" +
            "LEFT JOIN bookings b \n" +
            "    ON b.field_id = ps.field_id \n" +
            "    AND DATE(b.start_time) =?2  -- Lọc booking theo ngày\n" +
            "    AND TIME(b.start_time) < ps.end_time  \n" +
            "    AND TIME(b.end_time) > ps.start_time\n" +
            "WHERE ps.field_id = ?1\n" +
            "ORDER BY ps.start_time;",nativeQuery = true)
    List<Map<String,Object>> getTimeSlotInField(Long pitchId, LocalDate date);
    //trang chi tiet san

    @Query(value = "select * from fields where id=?1",nativeQuery = true)
    List<Map<String,Object>> getInfoById(Long pitchId);



}
