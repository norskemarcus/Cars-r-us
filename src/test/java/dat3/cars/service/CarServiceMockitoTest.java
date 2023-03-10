
package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CarServiceMockitoTest {

  @Mock
  CarRepository carRepository;

  CarService carService;

  @BeforeEach
  void setUp() {
    carService = new CarService(carRepository);
  }


  @Test
  void getCarsAdmin() {
    Car c1 = new Car("Tesla", "Model Y", 500, 20);
    Car c2 = new Car ("Tesla", "Model 3", 400, 20);
    // Tidsstempel
    c1.setCreated(LocalDateTime.now());
    c2.setCreated(LocalDateTime.now());
    // Mockito, hvis nogen bruger findAll-metoden, så returneres listen af m1 og m2
    Mockito.when(carRepository.findAll()).thenReturn(List.of(c1, c2));

    // Testen:
    List<CarResponse> cars = carService.getCars(true);
    assertEquals(2, cars.size());
    assertNotNull(cars.get(0).getCreated());
  }

  @Test
  void getCarsNotAdmin() {
    Car c1 = new Car("Tesla", "Model Y", 500, 20);
    Car c2 = new Car ("Tesla", "Model 3", 400, 20);
    // Tidsstempel
    c1.setCreated(LocalDateTime.now());
    c2.setCreated(LocalDateTime.now());
    // Mockito, hvis nogen bruger findAll-metoden, så returneres listen af m1 og m2
    Mockito.when(carRepository.findAll()).thenReturn(List.of(c1, c2));

    // Testen:
    List<CarResponse> cars = carService.getCars(false);
    assertEquals(2, cars.size());
    assertNull(cars.get(0).getCreated());
  }



  @Test
  void addCar() {
    // Integer id, String brand, String model, double pricePrDay
    Car c1 = new Car("Tesla", "Model Y", 500,10);
    Car c2 = new Car ("Tesla", "Model 3", 400, 10);
    // Tidsstempel
    c1.setCreated(LocalDateTime.now());
    c2.setCreated(LocalDateTime.now());

    // Mockito, hvis nogen bruger findAll-metoden, så returneres listen af m1 og m2
    Mockito.when(carRepository.findAll()).thenReturn(List.of(c1,c2));

    // Testen:
    List<CarResponse> cars = carService.getCars(true);
    assertEquals(2,cars.size());
    assertNotNull(cars.get(0).getCreated());
  }

  @Test
  void getCarById() {
    Car c1 = new Car("Tesla", "Model Y", 500, 10);
    c1.setCreated(LocalDateTime.now());
    Mockito.when(carRepository.findById(c1.getId())).thenReturn(java.util.Optional.of(c1));
    CarResponse response = carService.getCarById(c1.getId(), false);
    assertEquals(c1.getId(),response.getId());
  }

  @Test
  void editCar() {
    Car c1 = new Car("Tesla", "Model Y", 500, 15);
    c1.setCreated(LocalDateTime.now());
    Mockito.when(carRepository.findById(c1.getId())).thenReturn(java.util.Optional.of(c1));
    CarRequest request = new CarRequest(c1);
    request.setBrand("DS");
    carService.editCar(request, c1.getId());
    assertEquals("DS", request.getBrand());
  }


  @Test
  void setBestDiscountForCar() {
    Car c1 = new Car( "Tesla", "Model Y", 500, 10);

    c1.setCreated(LocalDateTime.now());
    Mockito.when(carRepository.existsById(c1.getId())).thenReturn(true);
    Mockito.when(carRepository.getReferenceById(c1.getId())).thenReturn(c1);

    carService.setBestDiscountForCar(c1.getId(), 50);
    assertEquals(50, c1.getBestDiscount());
  }




/*  @Test
  void deleteCarById() {

    Car c1 = new Car("Tesla", "Model Y", 500);
    Car c2 = new Car ("Tesla", "Model 3", 400);
    c1.setCreated(LocalDateTime.now());
    c2.setCreated(LocalDateTime.now());
    //CarRequest request = new CarRequest(c1);

    // Mockito, hvis nogen bruger findAll-metoden, så returneres listen af m1 og m2
    Mockito.when(carRepository.findAll()).thenReturn(List.of(c1,c2));
    carService.deleteCarById(c1.getId());

    // Testen:
    Mockito.when(carService.getCars(true)).thenReturn(List.of());
    List<CarResponse> cars = carService.getCars(true);

    assertEquals(1,cars.size());
    assertNull(cars.get(0).getCreated());

  }*/
}
