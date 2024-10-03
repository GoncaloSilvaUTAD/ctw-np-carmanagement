package com.ctw.car.boundary;

import com.ctw.car.control.CarRepository;
import com.ctw.car.control.CarService;
import com.ctw.car.control.CarUpdateDTO;
import com.ctw.car.entity.Car;
import com.ctw.car.entity.CarEntity;
import com.ctw.car.entity.EngineType;
import com.ctw.car.entity.Routes;
import io.quarkus.qute.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

import static io.quarkus.arc.ComponentsProvider.LOG;

@Path(Routes.CAR) // Routes.CAR = "car"
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {
    private final CarService carService;
    private final CarRepository carRepository;

    @Inject
    public CarResource(final CarService carService, final CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
    }


    @Inject
    Template index;
    @Inject
    Template create;
    @Inject
    Template view;
    @Inject
    Template edit;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public Response index(
            @QueryParam("brand") String brand,
            @HeaderParam("Accept") String acceptHeader
    ) {
        List<Car> cars = this.carService.getCars();
        if (acceptHeader != null && acceptHeader.contains(MediaType.TEXT_HTML)) {
            TemplateInstance content = index.data("cars", cars);
            return Response.ok(content.render()).type(MediaType.TEXT_HTML_TYPE).build();
        } else {
            return Response.ok(cars).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public Response showCreate()
    {
        return Response.ok(create.render()).type(MediaType.TEXT_HTML_TYPE).build();
    }

//  TODO: Add new fields in Car to POST method??
    @Path("/createSubmit")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    public Response createCar(@FormParam("brand") String brand, @FormParam("model") String model, @FormParam("engineType") EngineType engineTypeReceived)
    {
        System.out.println("Received car: ");
        System.out.println("brand: " + brand);
        System.out.println("model: " + model);
        System.out.println("engineType: " + engineTypeReceived);
        System.out.println("engineType.name(): " + engineTypeReceived.name());
        Car car2Add = new Car(model,brand,engineTypeReceived);
        car2Add.seats = 5;
        car2Add.autonomy = 600L;
        car2Add.licensePlate = "52-GA-23";
        car2Add.color = "Silver";
        carRepository.addCar(car2Add);
        List<Car> updatedCars = this.carService.getCars();
        TemplateInstance content = index.data("cars", updatedCars)
                .data("message", "Car created successfully");

        return Response.ok(content.render()).type(MediaType.TEXT_HTML_TYPE).build();
    }

    @Path("/{id}/view")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response showCarDetails(@PathParam("id") UUID id)
    {
        Car car = carRepository.findCarbyId(id);
        if(car == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Car not found").build();
        carRepository.checkCar(car);
        TemplateInstance content = view.data("car", car);
        return Response.ok(content.render()).build();
    }
    @Path("/{id}/edit")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response editCar(@PathParam("id") UUID id)
    {
        Car car = carRepository.findCarbyId(id);
        carRepository.checkCar(car);
        TemplateInstance content = edit.data("car", car);
        return Response.ok(content.render()).build();
    }

    @Path("/{id}/delete")
    @GET
    public Response deleteCar(UUID id)
    {
        carRepository.deleteCarByID(id);
        List<Car> updatedCars = this.carService.getCars();
        TemplateInstance content = index.data("cars", updatedCars)
                .data("message", "Car deleted successfully");

        return Response.ok(content.render()).type(MediaType.TEXT_HTML_TYPE).build();
    }

    @Path("/{id}/editSubmit")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    public Response editCarSubmit(@PathParam("id") UUID id,
                                  @FormParam("brand") String brand,
                                  @FormParam("model") String model, 
                                  @FormParam("engineType") EngineType engineTypeReceived,
                                  @FormParam("seats") Integer seats,
                                  @FormParam("autonomy") Long autonomy,
                                  @FormParam("licensePlate") String licensePlate,
                                  @FormParam("color") String color)
    {
        CarUpdateDTO carUpdateDTO = new CarUpdateDTO(model,brand,engineTypeReceived,seats,licensePlate,autonomy,color);
        Car updatedCar = carRepository.updateCar(id, carUpdateDTO);

        if(updatedCar == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Car to be edited not found").build();

        List<Car> updatedCars = this.carService.getCars();
        TemplateInstance content = index.data("cars", updatedCars)
                .data("message", "Car edited successfully");

        return Response.ok(content.render()).type(MediaType.TEXT_HTML_TYPE).build();
    }
}
