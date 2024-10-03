package com.ctw.car.control;

import com.ctw.car.entity.Car;
import com.ctw.car.entity.CarEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class CarRepository implements PanacheRepository<CarEntity> {

    @Inject
    EntityManager em;

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
    @Transactional
    public void deleteCarByID(UUID id)
    {
        CarEntity entity =em.find(CarEntity.class,id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public Car updateCar(UUID id, CarUpdateDTO carUpdateDTO)
    {
        Car oldCar = findCarbyId(id);
        Car newCar = new Car();
        if(oldCar == null)
            return null;
        else
        {
            if(carUpdateDTO.brand != null)
                newCar.setBrand(carUpdateDTO.brand);
            if(carUpdateDTO.model != null)
                newCar.setModel(carUpdateDTO.model);
            if(carUpdateDTO.color != null)
                newCar.setColor(carUpdateDTO.color);
            if(carUpdateDTO.engineType != null)
                newCar.setEngineType(carUpdateDTO.engineType);
            if (carUpdateDTO.autonomy == 0L)
                newCar.setAutonomy(carUpdateDTO.autonomy);
            if(carUpdateDTO.licensePlate != null)
                newCar.setLicensePlate(carUpdateDTO.licensePlate);
            if(carUpdateDTO.seats != 0)
                newCar.setSeats(carUpdateDTO.seats);

            deleteCarByID(oldCar.getId());
            addCar(newCar);
        }

        return newCar;
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
