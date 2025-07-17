package com.traicau.pitch.repository;

import com.traicau.pitch.model.Field;
import com.traicau.pitch.model.PricingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface PricingSlotRepository extends JpaRepository<PricingSlot,Long> {
    List<PricingSlot> findByField(Field field);

    @Query(value = "select *from pricing_slots where field_id=?1",nativeQuery = true)
    List<Map<String,Object>> getByFieldId(Long fieldId);

    @Query(value = "select *from pricing_slots where id=?1",nativeQuery = true)
    List<Map<String,Object>> getPricingSlotById(Long id);


}
