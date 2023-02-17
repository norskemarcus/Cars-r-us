package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {

  @Autowired
  CarRepository carRepository;
  @Autowired
  MemberRepository memberRepository;
  //ReservationService reservationService;
  boolean dataIsReady = false;
  List<Car> newCars;

  @BeforeEach
  void setUp() {

    newCars = new ArrayList<>(Arrays.asList(
     Car.builder().brand("Suzuki").model("SX4").pricePrDay(400).bestDiscount(16).build(),
     Car.builder().brand("Suzuki").model("SX4").pricePrDay(400).bestDiscount(16).build(),
     Car.builder().brand("Suzuki").model("SX4").pricePrDay(400).bestDiscount(16).build(),
     Car.builder().brand("Kia").model("Optima").pricePrDay(500).bestDiscount(18).build(),
     Car.builder().brand("WW").model("Wagon").pricePrDay(500).bestDiscount(20).build()));

    carRepository.saveAllAndFlush(newCars);
    //Real DB is mocked away with H2
   // reservationService = new ReservationService(reservationRepository, carRepository, memberRepository);
    dataIsReady = true;
  }

  @Test
  void findByBrandAndModel() {
    List<Car> foundCars = carRepository.findByBrandAndModel("Suzuki", "SX4");
    assertEquals(3, foundCars.size());
  }



  @Test
  void findAvgPricePrDayALlCars(){
    double avgPrice = carRepository.findAvgPricePrDayALlCars();
    // Average price per day of the cars in the set-up
    int expected = ((400*3) + (500*2)) / 5;
    assertEquals(expected, avgPrice);
  }




  @Test
  public void testFindAllCarsWithBestDiscount() {
    // Find the best discount
    Integer bestDiscount = carRepository.findMaxDiscount();
    // Find all cars with the best discount
    List<Car> cars = carRepository.findCarsByBestDiscount(bestDiscount);

    assertEquals(1, cars.size());
  }



}