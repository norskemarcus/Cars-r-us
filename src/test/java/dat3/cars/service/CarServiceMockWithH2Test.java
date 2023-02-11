package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//Transactional (rollback)
//Includes what is needed for JPA/Hibernate - leaves everything else out
//Uses an in-memory database (H2)
@DataJpaTest
class CarServiceMockWithH2Test {


  @Autowired
  public CarRepository carRepository;

  CarService carService;

  boolean dataIsReady = false;


  @BeforeEach
  void setUp() {
    if (!dataIsReady) {  //Explain this
      carRepository.save(new Car("Tesla", "Model Y", 500));
      carRepository.save(new Car("Volvo", "V70", 300));
      dataIsReady = true;
      carService = new CarService(carRepository); //Real DB is mocked away with H2
    }
  }

  @Test
  void addCar() {
    CarRequest request = new CarRequest();
    request.setId(1);
    request.setBrand("Opel");
    request.setModel("Astra");
    request.setPricePrDay(200);
    carService.addCar(request);
    assertEquals("Opel", request.getBrand());
  }


  @Test
  void getCars() {
    // set up laver 2 biler i databasen
    List<CarResponse> cars = carService.getCars(true);
    assertEquals(2, cars.size());
  }

  @Test
  void getCarById() {
    CarRequest request = new CarRequest();
    request.setId(1000);
    request.setBrand("Opel");
    request.setModel("Astra");
    request.setPricePrDay(200);
    CarResponse carResponse =  carService.addCar(request);

    if (carRepository.findById(carResponse.getId()).isPresent()) {
      Car car = carRepository.findById(carResponse.getId()).get();
      assertEquals(carResponse.getId(), car.getId());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this ID does not exist");
    }
  }

  @Test
  void editCar() {
    CarResponse response = carService.getCarById(1);

  }


  // Made with help from Tommy
  @Test
  void setBestDiscountForCar() {
    if (carRepository.findById(1).isPresent()) {
      Car c1 = carRepository.findById(1).get();
      c1.setCreated(LocalDateTime.now());

      carService.setBestDiscountForCar(1, 50);
      assertEquals(50, c1.getBestDiscount());
    }
  }


  // Er dette en dårlig test? Burde den hellere teste at dem throws an exception?
  @Test
  void setBestDiscountForCarNotPresent() {
    if (carRepository.findById(40000).isPresent()) {
      Car c1 = carRepository.findById(40000).get();
      c1.setCreated(LocalDateTime.now());
      carService.setBestDiscountForCar(40000, 50);
      assertEquals(0, c1.getBestDiscount());
    }
  }


  // Testen fejler hvis man kører alle samtidig...
  // Hjelp fra Tommy
  @Test
  void deleteCarById() {
    if (carRepository.findById(1).isPresent()){
      Car car = carRepository.findById(1).get();
      carService.deleteCarById(car.getId());
      assertFalse(carRepository.existsById(car.getId()));
    }

  }
}