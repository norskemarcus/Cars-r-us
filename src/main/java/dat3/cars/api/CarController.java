package dat3.cars.api;


import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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


  // ADMIN
  @GetMapping(path = "/{id}")
  CarResponse getCarById(@PathVariable Integer id) {
    return carService.getCarById(id);
  }


  // ANONYMOUS (real world: bekræfte per mail)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  CarResponse addCar(@RequestBody CarRequest body){
    // skal have samme navn som MemberRequest! Gør at den automatisk går fra json til java og vice versa
    return carService.addCar(body);
  }


  // Admin ? (eventually we will change it to use the currently log in user)
  @PutMapping("/{id}")
  ResponseEntity<Boolean> editCar(@RequestBody CarRequest body, @PathVariable Integer id){
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
  void deleteCarById(@PathVariable Integer id) {}






}

