package dat3.cars.service;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import dat3.security.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

  private ReservationRepository reservationRepository;
  private CarRepository carRepository;
  private MemberRepository memberRepository;


  public ReservationService(ReservationRepository reservationRepository,
  CarRepository carRepository, MemberRepository memberRepository) {
    this.reservationRepository = reservationRepository;
    this.carRepository = carRepository;
    this.memberRepository = memberRepository;
  }


  public ReservationResponse findReservationById(Integer id) {
    Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    return new ReservationResponse(reservation);
  }


  public List<ReservationResponse> findReservationsByMember(String username){

    // First check if the member exists
    Member memberFound = memberRepository.findById(username).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Member does not exist in the database"));

    List<Reservation> reservations = reservationRepository.findReservationsByMember_Username(username);

    if(reservations.isEmpty()){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, memberFound + " does not have any reservations");
    }
    return reservations.stream().map(ReservationResponse::new).toList();
  }



  public ReservationResponse makeReservation(ReservationRequest body) {

    // Check if the authenticated user is a valid member
    Member member = memberRepository.findById(body.getUsername()).orElseThrow(()
        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this ID does not exist"));

    // Add role to user
    member.addRole(Role.USER);

    // Check if the car exists
    Car car = carRepository.findById(body.getCarId()).orElseThrow(()
        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));

    // Checke om reservation er før dagens dato
    if (body.getRentalDate().isBefore(LocalDate.now())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The date is in the past");
    }
    // Checke om bilen er optaget den dag
    if(reservationRepository.existsByCarAndRentalDate(car, body.getRentalDate())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The car is already rented out");
    }
    // Map the request to a Reservation object
    Reservation reservation = new Reservation(car, member, body.getRentalDate());
    reservationRepository.save(reservation);
    return new ReservationResponse(reservation);
  }



  public List<ReservationResponse> getReservations() {
    List<Reservation> reservations = reservationRepository.findAll();
    return reservations.stream().map(r -> new ReservationResponse(r)).toList();
  }


// Find the total number of reservations made by a certain member
  public Integer getTotalNumberOfReservationsByMemberUsername(String username){
    return reservationRepository.findReservationsByMemberUsername(username);
  }




}