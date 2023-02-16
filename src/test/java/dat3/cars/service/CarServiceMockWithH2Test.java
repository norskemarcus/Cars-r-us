package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.server.ResponseStatusException;
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

  private Car car1;
  private Car car2;


  @BeforeEach
  void setUp() {
    if (dataIsReady) return;

      car1 = Car.builder().brand("Tesla").model("Model Y").pricePrDay(500).bestDiscount(10).build();
      car2 = Car.builder().brand("Volvo").model("V70").pricePrDay(300).bestDiscount(10).build();
      // KUN bruge dette i setUP i en test!

      carRepository.saveAndFlush(car1);
      carRepository.saveAndFlush(car2);
      carService = new CarService(carRepository); //Real DB is mocked away with H2

      dataIsReady = true;

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
    CarResponse response = carService.getCarById(car1.getId(),true);

    assertEquals(car1.getBrand(), response.getBrand());
    assertEquals(car1.getModel(), response.getModel());
    assertEquals(car1.getPricePrDay(), response.getPricePrDay());
    }


  @Test
  void editCar() {
    CarRequest request = new CarRequest(car1);
    request.setBrand("Kia");
    request.setModel("Ceed");
    request.setPricePrDay(400);
    carService.editCar(request, request.getId());

    assertEquals("Kia", request.getBrand());
  }


  @Test
  void setBestDiscountForCar() {
      carService.setBestDiscountForCar(car1.getId(), 50);
      assertEquals(50, car1.getBestDiscount());

  }


  @Test
  void deleteCarById() {
      carService.deleteCarById(car1.getId());
      assertFalse(carRepository.existsById(car1.getId()));
    }


// Test from Lars, example on test of Exception
  @Test
  void deleteNonExistingCarThrows() {
    assertThrows(ResponseStatusException.class, ()-> carService.deleteCarById(1111));
  }




  }
