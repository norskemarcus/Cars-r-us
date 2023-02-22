package dat3.cars.repository;
import dat3.cars.entity.Car;
import dat3.cars.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

  // Is there a reservation with this car at this rental date?
  @Query("select (count(r) > 0) from Reservation r where r.car = ?1 and r.rentalDate = ?2")
  boolean existsByCarAndRentalDate(Car car, LocalDate rentalDate);


  // Find reservations with a member username
  @Query("select r from Reservation r where r.member.username = ?1")
  List<Reservation> findReservationsByMember_Username(String username);


  // Find the total number of reservations made by a certain member
  @Query("select COUNT(r) from Reservation r where r.member.username = ?1")
  Integer findReservationsByMemberUsername(String name);
}
