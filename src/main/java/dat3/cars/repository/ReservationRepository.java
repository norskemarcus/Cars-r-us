package dat3.cars.repository;

import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

  boolean existsByCarAndRentalDate(Car car, LocalDate rentalDate);

  List<Reservation> findReservationsByMember_Username(String username);


  // Find the total number of reservations made by a certain member
  @Query("select COUNT(r) from Reservation r where r.member.username = ?1")
  Integer findReservationsByMemberUsername(String name);
}
