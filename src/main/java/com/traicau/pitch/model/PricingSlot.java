package com.traicau.pitch.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "pricing_slots")
public class PricingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Double price;

    public PricingSlot() {
    }

    public PricingSlot(Long id, Field field, LocalTime startTime, LocalTime endTime, Double price) {
        this.id = id;
        this.field = field;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }
    public PricingSlot( Field field, LocalTime startTime, LocalTime endTime, Double price) {
        this.field = field;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
