package dat3.cars.service;


import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repositories.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarService {

  CarRepository carRepository;

  public CarService (CarRepository carRepository){
    this.carRepository = carRepository;
  }


  public CarResponse addCar(CarRequest carRequest){
    //Add error checks --> Missing arguments, email taken etc.
    if(carRepository.existsById(carRequest.getId())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car with this ID already exist");
    }
    Car newCar = CarRequest.getCarEntity(carRequest);
    newCar = carRepository.save(newCar);
    return new CarResponse(newCar, false);  //False since anonymous uses can create "themself"
  }

  // OBS: returnerer en CarResponse! Bruge stream
  public List<CarResponse> getCars(boolean includeAll) {
    List<Car> cars = carRepository.findAll();
    List<CarResponse> carResponses = cars.stream().map(m -> new CarResponse(m, includeAll)).toList();
    return carResponses;
  }

  public CarResponse getCarById(Integer id) {
    return carRepository.getCarById(id);
  }

  public ResponseEntity<Boolean> editCar(CarRequest body, Integer id) {
    carRepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this ID does not exist"));
    Car editCar = CarRequest.getCarEntity(body);
    carRepository.save(editCar);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }
}
