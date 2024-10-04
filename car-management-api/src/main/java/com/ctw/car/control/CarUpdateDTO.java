package com.ctw.car.control;

import com.ctw.car.entity.EngineType;


public class CarUpdateDTO {
    public String model;
    public String brand;
    public EngineType engineType;
    public int seats;

    public String licensePlate;

    public Long autonomy;

    public String color;

    public CarUpdateDTO(String model, String brand, EngineType engineType, int seats, String licensePlate, Long autonomy, String color) {
        this.model = model;
        this.brand = brand;
        this.engineType = engineType;
        this.seats = seats;
        this.licensePlate = licensePlate;
        this.autonomy = autonomy;
        this.color = color;
        System.out.println("CarUpdateDTO before edit:" + this);
        checkCar(this);
    }

    public CarUpdateDTO(String model, String brand, EngineType engineType) {
        this.model = model;
        this.brand = brand;
        this.engineType = engineType;
    }

    private void checkCar(CarUpdateDTO carUpdateDTO)
    {

    }
}
