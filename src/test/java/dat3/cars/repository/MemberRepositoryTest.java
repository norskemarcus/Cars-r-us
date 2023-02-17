package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  CarRepository carRepository;
  @Autowired
  ReservationRepository reservationRepository;

  ReservationService reservationService;
  boolean dataIsReady = false;
  private Member m1;
  private Member m2;
  private Car car1;


  @BeforeEach
  void setUp() {
    // Cars
    car1 = Car.builder().brand("Volvo").model("V70").pricePrDay(500).bestDiscount(10).build();
    carRepository.saveAndFlush(car1);

    // Members
    m1 = new Member("kurt-w", "xx55", "test12@gmail.com", "Kurt", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800");
    memberRepository.saveAndFlush(m1);
    m2 = new Member("hanne", "gfdslkj", "hanne@gmail.com", "Hanne", "Panne", "Lyngbyvej 34", "Lyngby", "2800");
    memberRepository.saveAndFlush(m2);

    LocalDate rentalDate = LocalDate.parse("2023-02-14");
    LocalDate rentalDate2 = LocalDate.parse("2024-02-14");

    // Make reservations
    Reservation reservation = new Reservation(car1, m1, rentalDate);
    Reservation reservation2 = new Reservation(car1, m2, rentalDate2);

    reservationRepository.saveAndFlush(reservation);
    reservationRepository.saveAndFlush(reservation2);

    //Real DB is mocked away with H2
    reservationService = new ReservationService(reservationRepository, carRepository, memberRepository);
    dataIsReady = true;
  }

  @Test
  void findMembersWithReservation() {
      List<Member> members = memberRepository.findMembersWithReservation();
      assertEquals(2, members.size());
    }



  }
