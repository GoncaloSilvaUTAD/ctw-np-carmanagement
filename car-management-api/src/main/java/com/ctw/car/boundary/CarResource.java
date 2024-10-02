package com.ctw.car.boundary;

import com.ctw.car.control.CarService;
import com.ctw.car.entity.Car;
import com.ctw.car.entity.Routes;
import io.quarkus.qute.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.jboss.logging.Logger;

import java.util.List;

import static io.quarkus.arc.ComponentsProvider.LOG;

@Path(Routes.CAR) // Routes.CAR = "car"
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {

    private final CarService carService;

    @Inject
    public CarResource(final CarService carService) {
        this.carService = carService;
    }

    @Inject
    Template index;
    Template create;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public Response getCars(
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
    public TemplateInstance showCreate()
    {
        return create.instance();
    }

    @Path("/createSubmit")//maybe you don't need a path? Or just need different one?
    @POST
    public String createCar(Car car)
    {
        System.out.println("Received car: " + car);
        return "{\"message\": \"Car created successfully\"}";
    }
}
