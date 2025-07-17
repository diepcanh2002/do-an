package com.traicau.pitch.repository;

import com.traicau.pitch.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query(value = "select *from bookings b where b.status='CONFIRMED' and DATE(b.start_time)=?1 and b.field_id=?2 order by b.start_time asc",nativeQuery = true)
    List<Booking> getBookingByDayAndPitchId(LocalDate date, Long pitchId);

    @Query(value = "SELECT \n" +
            "   b.id,b.start_time,b.end_time,b.status,f.name as field,u.full_name,u.phone \n" +
            "FROM bookings b\n" +
            "INNER JOIN fields f ON f.id = b.field_id\n" +
            "inner join users u on u.id =b.user_id\n" +
            "WHERE (b.start_time BETWEEN ?1 AND ?2) and b.status!='CANCELED'",nativeQuery = true)
    List<Map<String,Object>> getAllBookingByRange(LocalDate startTime,LocalDate endTime);
    //admin


    @Query(value = "SELECT\n" +
            "b.id,\n" +
            "DATE(b.start_time) AS date,\n" +
            "TIME(b.start_time) AS start_time,\n" +
            "TIME(b.end_time) AS end_time,\n" +
            "f.name as field,\n" +
            "b.status,\n" +
            "f.address\n" +
            "FROM bookings b\n" +
            "INNER JOIN fields f ON f.id = b.field_id where b.user_id=?3 \n" +
            "AND b.start_time between ?1 AND ?2 ",nativeQuery = true)
    List<Map<String,Object>> getBookingByRangeByUserId(LocalDateTime startTime, LocalDateTime endTime, Long userId);


    @Query(value = "WITH status_list AS (\n" +
            "    SELECT 'pending' AS status, 'Chờ xác nhận' AS status_vi\n" +
            "    UNION ALL\n" +
            "    SELECT 'confirmed', 'Đã xác nhận'\n" +
            "    UNION ALL\n" +
            "    SELECT 'canceled', 'Đã hủy'\n" +
            ")\n" +
            "SELECT sl.status_vi, \n" +
            "       COALESCE(b.count, 0) AS count, \n" +
            "       COALESCE(b.ratio, 0) AS ratio\n" +
            "FROM status_list sl\n" +
            "LEFT JOIN (\n" +
            "    SELECT status, \n" +
            "           COUNT(id) AS count,\n" +
            "           COUNT(id) * 1.0 / NULLIF((\n" +
            "               SELECT COUNT(*) FROM bookings \n" +
            "               WHERE start_time BETWEEN \n" +
            "                   (CASE \n" +
            "                       WHEN :mode = 'day' THEN DATE(:date)\n" +
            "                       WHEN :mode = 'week' THEN DATE_SUB(DATE(:date), INTERVAL WEEKDAY(:date) DAY)\n" +
            "                       WHEN :mode = 'month' THEN DATE_SUB(DATE(:date), INTERVAL DAY(:date)-1 DAY)\n" +
            "                       WHEN :mode = 'year' THEN DATE_SUB(DATE(:date), INTERVAL DAYOFYEAR(:date)-1 DAY)\n" +
            "                   END) \n" +
            "               AND \n" +
            "                   (CASE \n" +
            "                       WHEN :mode = 'day' THEN DATE_ADD(DATE(:date), INTERVAL 1 DAY) \n" +
            "                       WHEN :mode = 'week' THEN DATE_ADD(DATE_SUB(DATE(:date), INTERVAL WEEKDAY(:date) DAY), INTERVAL 6 DAY) \n" +
            "                       WHEN :mode = 'month' THEN LAST_DAY(DATE(:date)) \n" +
            "                       WHEN :mode = 'year' THEN DATE_SUB(DATE_ADD(DATE(:date), INTERVAL 1 YEAR), INTERVAL DAYOFYEAR(DATE(:date)) DAY)\n" +
            "                   END)\n" +
            "           ), 0) AS ratio\n" +
            "    FROM bookings\n" +
            "    WHERE start_time BETWEEN \n" +
            "        (CASE \n" +
            "            WHEN :mode = 'day' THEN DATE(:date)\n" +
            "            WHEN :mode = 'week' THEN DATE_SUB(DATE(:date), INTERVAL WEEKDAY(:date) DAY)\n" +
            "            WHEN :mode = 'month' THEN DATE_SUB(DATE(:date), INTERVAL DAY(:date)-1 DAY)\n" +
            "            WHEN :mode = 'year' THEN DATE_SUB(DATE(:date), INTERVAL DAYOFYEAR(:date)-1 DAY)\n" +
            "         END)\n" +
            "    AND \n" +
            "        (CASE \n" +
            "            WHEN :mode = 'day' THEN DATE_ADD(DATE(:date), INTERVAL 1 DAY) \n" +
            "            WHEN :mode = 'week' THEN DATE_ADD(DATE_SUB(DATE(:date), INTERVAL WEEKDAY(:date) DAY), INTERVAL 6 DAY) \n" +
            "            WHEN :mode = 'month' THEN LAST_DAY(DATE(:date)) \n" +
            "            WHEN :mode = 'year' THEN DATE_SUB(DATE_ADD(DATE(:date), INTERVAL 1 YEAR), INTERVAL DAYOFYEAR(DATE(:date)) DAY)\n" +
            "         END)\n" +
            "    GROUP BY status\n" +
            ") b ON sl.status = b.status\n" +
            "ORDER BY sl.status;",nativeQuery = true)
    List<Map<String,Object>> getPieChart(@Param("date") LocalDate date, @Param("mode") String mode);

    @Query(value = "WITH numbers AS (\n" +
            "    SELECT 1 AS num UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 \n" +
            "    UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10\n" +
            "    UNION ALL SELECT 11 UNION ALL SELECT 12\n" +
            ")\n" +
            "SELECT \n" +
            "    CASE \n" +
            "        WHEN :mode = 'week' THEN \n" +
            "            DATE_FORMAT(DATE_ADD(STR_TO_DATE(:date, '%Y-%m-%d'), INTERVAL (n.num - DAYOFWEEK(STR_TO_DATE(:date, '%Y-%m-%d'))) DAY), '%d/%m/%Y')\n" +
            "        WHEN :mode = 'month' THEN \n" +
            "            CONCAT('Tuần ', n.num + WEEK(STR_TO_DATE(:date, '%Y-%m-%d'), 1) - WEEK(DATE_SUB(STR_TO_DATE(:date, '%Y-%m-%d'), INTERVAL DAY(STR_TO_DATE(:date, '%Y-%m-%d'))-1 DAY), 1))\n" +
            "        WHEN :mode = 'year' THEN \n" +
            "            CONCAT('Tháng ', LPAD(n.num, 2, '0'))\n" +
            "    END AS period, \n" +
            "    COALESCE(COUNT(b.id), 0) AS booking_count\n" +
            "FROM numbers n\n" +
            "LEFT JOIN bookings b ON (\n" +
            "    (:mode = 'week' AND n.num = DAYOFWEEK(b.start_time) \n" +
            "        AND YEARWEEK(b.start_time, 1) = YEARWEEK(STR_TO_DATE(:date, '%Y-%m-%d'), 1))\n" +
            "    OR (:mode = 'month' AND n.num = (WEEK(b.start_time, 1) - WEEK(DATE_SUB(b.start_time, INTERVAL DAY(b.start_time)-1 DAY), 1) + 1) \n" +
            "        AND YEAR(b.start_time) = YEAR(STR_TO_DATE(:date, '%Y-%m-%d')) \n" +
            "        AND MONTH(b.start_time) = MONTH(STR_TO_DATE(:date, '%Y-%m-%d')) )\n" +
            "    OR (:mode = 'year' AND n.num = MONTH(b.start_time) \n" +
            "        AND YEAR(b.start_time) = YEAR(STR_TO_DATE(:date, '%Y-%m-%d')))\n" +
            ")\n" +
            "WHERE \n" +
            "    (:mode = 'week' AND n.num BETWEEN 1 AND 7)\n" +
            "    OR (:mode = 'month' AND n.num BETWEEN 1 AND 5)\n" +
            "    OR (:mode = 'year' AND n.num BETWEEN 1 AND 12)\n" +
            "GROUP BY period\n" +
            "ORDER BY period;",nativeQuery = true)
    List<Map<String,Object>> getColumChart(@Param("date") LocalDate date, @Param("mode") String mode);

}
