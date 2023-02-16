package dat3.cars.service;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

@DataJpaTest
@ComponentScan("dat3.car.service")
class ReservationServiceTestH2 {

  @Autowired
  public ReservationRepository reservationRepository;
  public MemberRepository memberRepository;
  public CarRepository carRepository;

  private ReservationService reservationService;
  private Member m1;
  private Member m2;
  private Car car1;
  private Car car2;
  boolean dataIsReady = false;

  @BeforeEach
  void setUp() {

    if(!dataIsReady){
      m1 = memberRepository.save(new Member("kurt-w", "kw@a.dk", "test12", "Kurt", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));
      m2 = memberRepository.save(new Member("hanne-w", "hw@a.dk", "test12", "Hanne", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));

      car1 = Car.builder().brand("Tesla").model("Model Y").pricePrDay(500).bestDiscount(10).build();
      car2 = Car.builder().brand("Volvo").model("V70").pricePrDay(300).bestDiscount(10).build();
      carRepository.save(car1);
      carRepository.save(car2);

      // Oprette en dato
      LocalDate rentalDate = LocalDate.parse("2023-02-15");

      // Car car, Member member, LocalDate rentalDate
      reservationRepository.save(new Reservation(car1, m1, rentalDate));

      carRepository.saveAndFlush(car1);
      carRepository.saveAndFlush(car2);

      //Real DB is mocked away with H2
      //TODO: update with userRepo
      // reservationService = new ReservationService(reservationRepository, carRepository, memberRepository, userRepo);

      dataIsReady = true;
    }
  }
/*

  @Test
  void makeReservation() {
    ReservationRequest body = new ReservationRequest();
    body.setCarId(car1.getId());
    //body.setUsername(m1.getUser());
    body.setRentalDate(LocalDate.parse("2023-02-15"));

    ReservationResponse response = reservationService.makeReservation(body);

    assertEquals("2023-02-15", body.getRentalDate());
  }
*/


}