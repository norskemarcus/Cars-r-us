package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Uses H2 as a in-memory database to the JPA repository
@DataJpaTest
@ComponentScan("dat3.car.repository")
class ReservationRepositoryTest {


  @Autowired
  CarRepository carRepository;
  @Autowired
  ReservationRepository reservationRepository;
  @Autowired
  MemberRepository memberRepository;

  ReservationService reservationService;
  boolean dataIsReady = false;
  private Member m1;
  private Member m2;
  private Car car1;
  private Car car2;
  List<Car> newCars;

  @BeforeEach
  void setUp() {
    Car volvo = Car.builder().brand("Volvo").model("V70").pricePrDay(500).bestDiscount(10).build();
    carRepository.saveAndFlush(volvo);

    m1 = new Member("kurt-w", "xx55", "test12@gmail.com", "Kurt", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800");
    memberRepository.saveAndFlush(m1);

    LocalDate rentalDate = LocalDate.parse("2023-02-14");
    LocalDate rentalDate2 = LocalDate.parse("2024-02-14");
    LocalDate rentalDate3 = LocalDate.parse("2024-02-14");

    // Make 3 reservations to member m1
    Reservation reservation = new Reservation(volvo, m1, rentalDate);
    Reservation reservation2 = new Reservation(volvo, m1, rentalDate2);
    Reservation reservation3 = new Reservation(volvo, m1, rentalDate3);
    reservationRepository.saveAndFlush(reservation);
    reservationRepository.saveAndFlush(reservation2);
    reservationRepository.saveAndFlush(reservation3);

    //Real DB is mocked away with H2
    reservationService = new ReservationService(reservationRepository, carRepository, memberRepository);
    dataIsReady = true;
  }

  @Test
  void findReservationsByMember_Username() {
    // Find all the reservations that "kurt-w" has (m1)
    List<Reservation> reservations = reservationRepository.findReservationsByMember_Username(m1.getUsername());
    assertEquals(3, reservations.size());
  }
}