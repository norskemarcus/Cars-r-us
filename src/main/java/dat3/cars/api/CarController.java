package dat3.cars.api;


import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cars")
public class CarController {

  final CarService carService;

  public CarController(CarService carService) {
    this.carService = carService;
  }

  // ADMIN
  @GetMapping
  List<CarResponse> getCars() {
    return carService.getCars(true); // true since admin!
  }


  // ANONYMOUS?
  @GetMapping(path = "/{id}")
  CarResponse getCarById(@PathVariable Integer id) {
    return carService.getCarById(id, true); // Skal denne være false?
  }


  // ANONYMOUS (real world: bekræfte per mail)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  CarResponse addCar(@RequestBody CarRequest body) {
    return carService.addCar(body);
  }


  // Admin ? (eventually we will change it to use the currently log in user)
  @PutMapping("/{id}")
  ResponseEntity<Boolean> editCar(@RequestBody CarRequest body, @PathVariable Integer id) {
    return carService.editCar(body, id);
  }

  // Patch: Ændre en del af tabellen
  // ADMIN
  @PatchMapping("/discount/{id}/{value}")
  void setBestDiscount(@PathVariable Integer id, @PathVariable Integer value) {
    carService.setBestDiscountForCar(id, value);
  }


  // ADMIN
  @DeleteMapping("/{id}")
  void deleteCarById(@PathVariable Integer id) {
    carService.deleteCarById(id);
  }

  // Find all cars with a certain brand and model
  // Member
  @GetMapping(path = "/brand-model/{brand}/{model}")
  List<CarResponse> getCarsByCertainBrandAndModel(@PathVariable String brand, @PathVariable String model) {
    return carService.getCarsByBrandAndModel(brand, model); // OBS tager ikke stilling til false og true
  }


  @GetMapping("/discount")
  List<CarResponse> getCarsWithBestDiscount(){
    return carService.getCarsWithBestDiscount();
  }

  // http://localhost:8080/api/cars/no-reservation
  @GetMapping("/no-reservation")
  List<CarResponse> getCarsWithoutReservation(){
    return carService.getCarsWithoutReservation();
  }

}

