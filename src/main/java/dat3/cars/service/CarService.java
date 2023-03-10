package dat3.cars.service;


import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarService {

  private final CarRepository carRepository;


  public CarService (CarRepository carRepository){
    this.carRepository = carRepository;
  }

  /**
   * @param carRequest
   * @return {Value} TODO: hvordan få value op?
   *
   */
  public CarResponse addCar(CarRequest carRequest){

    if(carRequest.getId() != null && carRepository.existsById(Math.toIntExact(carRequest.getId()))){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car with this ID already exist");
    }
    Car newCar = CarRequest.getCarEntity(carRequest);
    newCar = carRepository.save(newCar);
    return new CarResponse(newCar, false);  //False since anonymous uses can create "themself"
  }


  public List<CarResponse> getCars(boolean includeAll) {
    List<Car> cars = carRepository.findAll();
    return cars.stream().map(m -> new CarResponse(m, includeAll)).toList();
  }

  public CarResponse getCarById(Integer id, boolean includeAll) {
    Car found = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found"));
    return new CarResponse(found, includeAll);
  }


  public ResponseEntity<Boolean> editCar(CarRequest body, Integer id) {
        Car carToEdit = carRepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this ID does not exist"));

    carToEdit.setBrand(body.getBrand());
    carToEdit.setModel(body.getModel());
    carToEdit.setPricePrDay(body.getPricePrDay());
    carRepository.save(carToEdit);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }


  public void setBestDiscountForCar(Integer id, Integer value) {

    if(carRepository.existsById(id)){
       Car car = carRepository.getReferenceById(id);
       car.setBestDiscount(value);
       carRepository.save(car);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this ID does not exist");
    }
  }

// Kan også bruge ResponseEntity<Boolean>, if not exists -> throw new ResponseStatusException: with try and catch
  public void deleteCarById(Integer id) {
    carRepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this ID does not exist"));

    carRepository.deleteById(id);
  }


  public List<CarResponse> getCarsByBrandAndModel(String brand, String model) {
    List<Car> cars = carRepository.findByBrandAndModel(brand, model);
    return cars.stream().map(c -> new CarResponse(c, false)).toList(); // false since member
  }


  public List<CarResponse> getCarsWithBestDiscount(){
    Integer bestDiscount = carRepository.findMaxDiscount();
    List<Car> cars = carRepository.findCarsByBestDiscount(bestDiscount);
    return cars.stream().map(c -> new CarResponse(c, true)).toList();
  }

  public List<CarResponse> getCarsWithoutReservation(){
    List<Car> cars = carRepository.getCarsWithoutReservation();
    return cars.stream().map(c -> new CarResponse(c, true)).toList();
  }

}
