package dat3.cars.service;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ReservationServiceTest {

  @Autowired
  public ReservationRepository reservationRepository;
  @Autowired
  public MemberRepository memberRepository;
  @Autowired
  public CarRepository carRepository;

  ReservationService reservationService;

  private Member m1;
  private Member m2;
  private Car car1;
  private Car car2;
  boolean dataIsReady = false;



  @BeforeEach
  void setUp() {

    if(!dataIsReady){
      car1 = new Car("Tesla", "Model Y", 500, 10);
      car2 = new Car ("Volvo" , "V70", 300, 10);
      carRepository.saveAndFlush(car1);
      carRepository.saveAndFlush(car2);


      m1 = new Member("kurt-w", "xx55", "test12@gmail.com", "Kurt", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800");
      m2 = new Member("hanne-w", "xx66", "hw@a.dk", "Hanne", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800");
      memberRepository.saveAndFlush(m1);
      memberRepository.saveAndFlush(m2);

      // Make 2 reservations to member m1
      LocalDate rentalDate = LocalDate.parse("2023-02-14");
      LocalDate rentalDate2 = LocalDate.parse("2024-02-14");
      Reservation reservation = new Reservation(car1, m2, rentalDate);
      Reservation reservation2 = new Reservation(car2, m2, rentalDate2);
      reservationRepository.saveAndFlush(reservation);
      reservationRepository.saveAndFlush(reservation2);

      //Real DB is mocked away with H2
       reservationService = new ReservationService(reservationRepository, carRepository, memberRepository);

      dataIsReady = true;
    }
  }


  @Test
  void makeReservation() {
    ReservationRequest body = new ReservationRequest();
    body.setCarId(car1.getId());
    body.setUsername(m1.getUsername());
    body.setRentalDate(LocalDate.parse("2023-08-20"));

    ReservationResponse response = reservationService.makeReservation(body);

    assertEquals(LocalDate.parse("2023-08-20"), response.getRentalDate());
  }


  @Test
  void findReservationsByMember(){
    List<ReservationResponse> reservations = reservationService.findReservationsByMember(m2.getUsername());
    // 2 since member m2 made 2 reservations in the set-up
    assertEquals(2, reservations.size());
  }

  @Test
  void getTotalNumberOfReservationsByMemberUsername(){
    Integer numberOfReservations = reservationService.getTotalNumberOfReservationsByMemberUsername(m2.getUsername());
    // Member m2 has 2 reservation
    assertEquals(2, numberOfReservations);
  }

}