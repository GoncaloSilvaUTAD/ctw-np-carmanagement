package com.ctw.car.entity;

import jakarta.persistence.Column;

import java.util.UUID;

public class Car {

    private UUID id;
    private String model;
    private String brand;
    private EngineType engineType;
    public int seats;
    public String licensePlate;
    public Long autonomy;
    public String color;

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(Long autonomy) {
        this.autonomy = autonomy;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public Car(String model, String brand, EngineType engineType, int seats, String licensePlate, Long autonomy, String color) {
        this.model = model;
        this.brand = brand;
        this.engineType = engineType;
        this.seats = seats;
        this.licensePlate = licensePlate;
        this.autonomy = autonomy;
        this.color = color;
    }

    public Car() {

    }

    public Car(String model, String brand, EngineType engineType) {
        this.model = model;
        this.brand = brand;
        this.engineType = engineType;
    }

    public Car(UUID id, String brand, String model, EngineType engineType) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.engineType = engineType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }
}
