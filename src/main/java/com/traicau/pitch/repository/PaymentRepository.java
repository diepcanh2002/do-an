package com.traicau.pitch.repository;

import com.traicau.pitch.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Query(value = "select p.id,u.full_name,b.start_time,f.name as field,\n" +
            "p.total ,p.payment_date,p.payment_method,p.status,TIMESTAMPDIFF(minute,start_time,end_time) as duration\n" +
            "from payments p \n" +
            "inner join bookings b ON p.booking_id =b.id\n" +
            "inner join fields f ON f.id =b.field_id \n" +
            "inner join users u on b.user_id=b.user_id \n",nativeQuery = true)
    List<Map<String,Object>> getAllPayment();


    @Query(value = "select\n" +
            "f.name as field,f.`type` as type,f.address,\n" +
            "DATE(b.start_time)as date, TIME(b.start_time)as start_time ,TIME(b.end_time)as end_time,\n" +
            "TIMESTAMPDIFF(minute,start_time,end_time) as duration,p.total,p.status,p.payment_method,p.payment_date \n" +
            "from payments p \n" +
            "inner join bookings b on b.id =p.id \n" +
            "inner join fields f on f.id =b.field_id \n" +
            "where p.booking_id =?1\n",nativeQuery = true)
    List<Map<String,Object>> getBillByBookingId(long bookingId);

    @Query(value = "select p.id,u.full_name,b.start_time,f.name as field,\n" +
            "p.total ,p.payment_date,p.payment_method,p.status,TIMESTAMPDIFF(minute,start_time,end_time) as duration\n" +
            "from payments p \n" +
            "left join bookings b ON p.booking_id =b.id\n" +
            "left join fields f ON f.id =b.field_id \n" +
            "left join users u on u.id =b.user_id \n" +
            "where b.start_time between ?1 and ?2 \n" +
            "group by p.id \n" +
            "order by b.start_time asc",nativeQuery = true)
    List<Map<String,Object>> getByTimeRange(LocalDate startTime, LocalDate endTime);


    @Query(value = "WITH RECURSIVE calendar_table AS (\n" +
            "    -- Lấy ngày bắt đầu của khoảng thời gian\n" +
            "    SELECT \n" +
            "        CASE \n" +
            "            WHEN :mode = 'week' THEN DATE_SUB(:date, INTERVAL WEEKDAY(:date) DAY) \n" +
            "            WHEN :mode = 'month' THEN DATE_SUB(:date, INTERVAL DAYOFMONTH(:date)-1 DAY)\n" +
            "            WHEN :mode = 'year' THEN DATE_SUB(:date, INTERVAL MONTH(:date)-1 MONTH)\n" +
            "        END AS time_group\n" +
            "    UNION ALL\n" +
            "    -- Tạo các giá trị tiếp theo\n" +
            "    SELECT \n" +
            "        CASE \n" +
            "            WHEN :mode = 'week' THEN DATE_ADD(time_group, INTERVAL 1 DAY) \n" +
            "            WHEN :mode = 'month' THEN DATE_ADD(time_group, INTERVAL 1 WEEK) \n" +
            "            WHEN :mode = 'year' THEN DATE_ADD(time_group, INTERVAL 1 MONTH)\n" +
            "        END\n" +
            "    FROM calendar_table\n" +
            "    WHERE \n" +
            "        ( :mode = 'week' AND time_group < DATE_ADD(DATE_SUB(:date, INTERVAL WEEKDAY(:date) DAY), INTERVAL 6 DAY) ) \n" +
            "        OR \n" +
            "        ( :mode = 'month' AND time_group < LAST_DAY(:date) ) \n" +
            "        OR \n" +
            "        ( :mode = 'year' AND time_group < MAKEDATE(YEAR(:date), 1) + INTERVAL 11 MONTH)\n" +
            ")\n" +
            "SELECT \n" +
            "    CASE \n" +
            "        WHEN :mode = 'month' THEN \n" +
            "            CONCAT('Tuần ', WEEK(c.time_group, 3))\n" +
            "        WHEN :mode = 'year' THEN \n" +
            "            CONCAT('Tháng ', MONTH(c.time_group))\n" +
            "        ELSE \n" +
            "            DATE_FORMAT(c.time_group, '%Y-%m-%d')\n" +
            "    END AS formatted_time_group,\n" +
            "    COALESCE(SUM(p.total), 0) AS revenue\n" +
            "FROM calendar_table c\n" +
            "LEFT JOIN payments p \n" +
            "ON \n" +
            "    ( :mode = 'week' AND DATE(p.payment_date) = c.time_group ) \n" +
            "    OR \n" +
            "    ( :mode = 'month' AND YEARWEEK(p.payment_date, 3) = YEARWEEK(c.time_group, 3) ) \n" +
            "    OR \n" +
            "    ( :mode = 'year' AND YEAR(p.payment_date) = YEAR(c.time_group) AND MONTH(p.payment_date) = MONTH(c.time_group) )\n" +
            "WHERE p.status = 'PAID' OR p.status IS NULL\n" +
            "GROUP BY c.time_group\n" +
            "ORDER BY c.time_group",nativeQuery = true)
    List<Map<String,Object>> getLineChart(@Param("date") LocalDate date, @Param("mode") String mode);

}
