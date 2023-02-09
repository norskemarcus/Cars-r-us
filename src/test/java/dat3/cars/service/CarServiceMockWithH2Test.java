package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.entity.Car;
import dat3.cars.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarServiceMockWithH2Test {



  @Autowired
  public CarRepository carRepository;

  CarService carService;

  boolean dataIsReady = false;



  @BeforeEach
  void setUp() {
    if(!dataIsReady){  //Explain this
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
  }

  @Test
  void getCarById() {
  }

  @Test
  void editCar() {
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
      if (carRepository.findById(40).isPresent()) {
        Car c1 = carRepository.findById(40).get();
        c1.setCreated(LocalDateTime.now());
        carService.setBestDiscountForCar(1, 50);
        assertEquals(0, c1.getBestDiscount());
      }
    }


// Testen fejler hvis man kører alle samtidig...
  @Test
  void deleteCarById() {
    // Det burde være 2 biler i repo på forhånd fra setup
    List<Car> cars = carRepository.findAll();
    carService.deleteCarById(1);
    //assertNull(cars.get(0).getCreated());
    assertEquals(1, cars.size());
  }
}