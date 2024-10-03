package com.ctw.car.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "T_CAR")
public class CarEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    public UUID id;

    @Column(name = "BRAND", nullable = false)
    public String brand;

    @Column(name = "MODEL", nullable = false)
    public String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "ENGINE_TYPE", nullable = false)
    public EngineType engineType;

    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    public LocalDateTime createdAt;

    @Column(name = "CREATED_BY", updatable = false)
    public String createdBy;

    @Column(name = "SEATS")
    public int seats;

    @Column(name = "LICENSE_PLATES")
    public String licensePlates;

    @Column(name = "AUTONOMY")
    public Long autonomy;

    @Column(name = "COLOR")
    public String color;

    public static Car toCar(CarEntity carEntity) {
        if (Objects.nonNull(carEntity)) {
            return new Car(carEntity.id, carEntity.brand, carEntity.model, carEntity.engineType,carEntity.seats,carEntity.licensePlates,carEntity.autonomy,carEntity.color);
        }
        return null;
    }

    public CarEntity(Car car)
    {
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.engineType = car.getEngineType();
        this.createdAt = LocalDateTime.now();
        this.seats = car.getSeats();
        this.autonomy = car.getAutonomy();
        this.color = car.getColor();
        this.licensePlates = car.getLicensePlate();


    }

    public CarEntity() {
    }
}
