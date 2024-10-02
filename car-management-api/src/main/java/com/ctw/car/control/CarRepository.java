package com.ctw.car.control;

import com.ctw.car.entity.Car;
import com.ctw.car.entity.CarEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class CarRepository implements PanacheRepository<CarEntity> {
    public List<Car> fetchAllCars() {
        return listAll()
                .stream()
                .map(CarEntity::toCar)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    @Transactional
    public void addCar(Car car) {
        CarEntity carEntity = new CarEntity(car);
        persist(carEntity);
    }

    public Car findCarbyId(UUID id)
    {
        List<Car> allCars = fetchAllCars();
        for (Car car : allCars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    public Car updateCar(UUID id, CarUpdateDTO carUpdateDTO)
    {
        Car car = findCarbyId(id);
        if(car == null)
            return null;
        else
        {
            if(carUpdateDTO.brand != null)
                car.setBrand(carUpdateDTO.brand);
            if(carUpdateDTO.model != null)
                car.setModel(carUpdateDTO.model);
            if(carUpdateDTO.color != null)
                car.setColor(carUpdateDTO.color);
            if(carUpdateDTO.engineType != null)
                car.setEngineType(carUpdateDTO.engineType);
            if (carUpdateDTO.autonomy == 0L)
                car.setAutonomy(carUpdateDTO.autonomy);
            if(carUpdateDTO.licensePlate != null)
                car.setLicensePlate(carUpdateDTO.licensePlate);
            if(carUpdateDTO.seats != 0)
                car.setSeats(carUpdateDTO.seats);

            addCar(car);
        }

        return car;
    }
    
    public void checkCar(Car car)
    {
        if(car.color == null)
        {
            car.autonomy = 600L;
        }
        else if( car.seats == 0)
        {
            car.seats = 5;
        }
        else if( car.licensePlate == null)
        {
            car.licensePlate = "52-GA-23";
        }
        else if( car.color == null)
        {
            car.color = "Silver";
        }
    }
}
