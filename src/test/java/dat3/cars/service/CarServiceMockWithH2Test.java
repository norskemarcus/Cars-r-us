package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
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
    Car car = new Car("TestBrand", "1000X", 1000);
    CarRequest request = new CarRequest(car);

      if (carRepository.existsById(request.getId())) {
        CarResponse carFound = carRepository.getCarById(request.getId());
        assertEquals(car.getId(), carFound.getId());
      }
    }


  @Test
  void editCar() {
    Car car = new Car("TestBrand", "1000X", 1000);
    if (carRepository.existsById(car.getId())){
      CarRequest request = new CarRequest(car);
      request.setBrand("Kia");
      request.setModel("Ceed");
      request.setPricePrDay(400);
      carService.editCar(request, request.getId());
      assertEquals("Kia", car.getBrand());
    }
  }


  @Test
  void setBestDiscountForCar() {

    Car car = new Car("TestBrand", "1000X", 1000);
    CarRequest request = new CarRequest(car);

    if (carRepository.existsById(request.getId())) {
      Car c1 = carRepository.findById(request.getId()).get();
      c1.setCreated(LocalDateTime.now());

      carService.setBestDiscountForCar(car.getId(), 50);
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



  @Test
  void deleteCarById() {
    Car car = new Car("TestBrand", "1000X", 1000);
    CarRequest request = new CarRequest(car);

    if (carRepository.findById(request.getId()).isPresent()){
      carRepository.findById(request.getId()).get();
      carService.deleteCarById(car.getId());
      assertFalse(carRepository.existsById(car.getId()));
    }
  }
}